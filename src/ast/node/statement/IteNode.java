package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.type.BoolTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.SimpLanPlusLib.cloneEnvironment;
import static util.SimpLanPlusLib.iteStatus;


/**
 * If-then-else statement node.
 *
 * ite : 'if' '(' exp ')' statement ('else' statement)?;
 */

public class IteNode implements Node {

    private Node cond;
    private Node th;
    private Node el;
    private boolean isElNull = false;

    public IteNode(Node cond, Node th, Node el) {
        this.cond = cond;
        this.th = th;
        this.el = el;
    }

    public boolean getIsElNull() {
        return this.isElNull;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(cond.checkSemantics(env));

        res.addAll(th.checkSemantics(env));
        if (el != null)
            res.addAll(el.checkSemantics(env));
        else {
            /*
             * Note that we can have blocks with nested if-then-else nodes. In this
             * situation we want to detect return nodes in then/else branches, but we can
             * end up with just an if-then statement in the nested structure. Our decision
             * is to not take this situation into account when checking if a block has at least
             * one valid return statement.
             */
            this.isElNull = true;
        }
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node cond_type = cond.typeCheck(typeErr);

        // Condition must be BoolTypeNode
        if (cond_type.getPointLevel() != 0
                || !(SimpLanPlusLib.isSubtype(cond_type, new BoolTypeNode(0, Status.DECLARED)))) {
            typeErr.add(new SemanticError("non boolean condition in if"));
        }

        Node t = th.typeCheck(typeErr);
        if (el != null) {

            Node e = el.typeCheck(typeErr);
            // Now here we consider comments made in checkSemantics
            if (el instanceof IteNode) {
                /*
                 * This boolean expression allows to report the fact that this IteNode has a
                 * nested if-then statement
                 */
                this.isElNull = ((IteNode) el).getIsElNull() || this.isElNull;
            }

            // Both then and else branch must have same type
            if (SimpLanPlusLib.isSubtype(t, e))
                return e;
            typeErr.add(new SemanticError("Incompatible types in then else branches"));
        }
        return t;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(cond.checkEffects(env));

        if (el != null) {

            /*
             * In if-then-else effect analysis we have to create two different environments
             * in which continuing effect analysis of the two different branches. This is
             * why two different copy of env are made
             */

            Environment envThen = cloneEnvironment(env);
            Environment envElse = cloneEnvironment(env);

            res.addAll(th.checkEffects(envThen));
            res.addAll(el.checkEffects(envElse));

            // Now we perform the max operation between the two environments
            for (HashMap<String, STentry> map : env.symTable) {
                for (Map.Entry<String, STentry> entry : map.entrySet()) {
                    String key = entry.getKey();
                    int nestLevel = entry.getValue().getNestinglevel();
                    Status th = envThen.symTable.get(nestLevel).get(key).getType().getStatus();
                    Status el = envElse.symTable.get(nestLevel).get(key).getType().getStatus();
                    Status max = iteStatus(th, el);
                    STentry newEntry;
                    if (max == th) {
                        newEntry = envThen.symTable.get(nestLevel).get(key);
                    } else {
                        newEntry = envElse.symTable.get(nestLevel).get(key);
                    }
                    // Upload symbol table
                    env.symTable.get(nestLevel).replace(key, newEntry);
                }
            }
        } else {
            res.addAll(th.checkEffects(env));
        }
        return res;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        String str = indent + "Ite\n" + cond.toPrint(indent + "\t") + th.toPrint(indent + "\t");
        builder.append(str);
        if (el != null) {
            builder.append(el.toPrint(indent + "\t"));
        }
        return builder.toString();
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        Label elseLabel = new Label();
        Label endLabel = new Label();

        String ite = this.cond.codeGeneration(env) +
                // Load false (0) in $t0
                "li $t0 0\n" +
                // Condition boolean result is in $a0
                "beq " + elseLabel.getLabel() + " $a0 $t0\n" +
                this.th.codeGeneration(env) +
                "b " + endLabel.getLabel() + "\n" +
                elseLabel.getLabel() + ":\n";
        if (this.el != null)
            ite += this.el.codeGeneration(env);
        ite += endLabel.getLabel() + ":\n";
        return ite;

    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }
}