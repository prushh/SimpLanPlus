package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.type.BoolTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.SimpLanPlusLib.cloneEnvironment;
import static util.SimpLanPlusLib.maxStatus;

public class IteNode implements Node {

    private Node cond;
    private Node th;
    private Node el;

    public IteNode(Node cond, Node th, Node el) {
        this.cond = cond;
        this.th = th;
        this.el = el;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        String str = indent + "Ite\n" +
                cond.toPrint(indent + "\t") +
                th.toPrint(indent + "\t");
        builder.append(str);
        if (el != null) {
            builder.append(el.toPrint(indent + "\t"));
        }
        return builder.toString();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node cond_type = cond.typeCheck(typeErr);
        if (cond_type.getPointLevel() != 0 || !(SimpLanPlusLib.isSubtype(cond_type, new BoolTypeNode(0, Status.DECLARED)))) {
            typeErr.add(new SemanticError("non boolean condition in if"));
        }
        Node t = th.typeCheck(typeErr);
        if (el != null) {
            Node e = el.typeCheck(typeErr);
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

            Environment envThen = cloneEnvironment(env);
            Environment envElse = cloneEnvironment(env);

            res.addAll(th.checkEffects(envThen));
            res.addAll(el.checkEffects(envElse));

            for (HashMap<String, STentry> map : env.symTable) {
                for (Map.Entry<String, STentry> entry : map.entrySet()) {
                    String key = entry.getKey();
                    int nestLevel = entry.getValue().getNestinglevel();
                    Status th = envThen.symTable.get(nestLevel).get(key).getType().getStatus();
                    Status el = envElse.symTable.get(nestLevel).get(key).getType().getStatus();
                    Status max = maxStatus(th, el);
                    STentry newEntry;
                    if (max == th) {
                        newEntry = envThen.symTable.get(nestLevel).get(key);
                    } else {
                        newEntry = envElse.symTable.get(nestLevel).get(key);
                    }
                    env.symTable.get(nestLevel).replace(key, newEntry);
                }
            }
        } else {
            res.addAll(th.checkEffects(env));
        }
        return res;
    }

    @Override
    public String codeGeneration(int nestingLevel) {
        Label label = new Label();
        return this.cond.codeGeneration(nestingLevel) +
                "li $t0 0\n" +
                // l is the false branch label got through newLabel() func
                "beq " + label.getLabel() + " $a0 $t0\n" +
                this.th.codeGeneration(nestingLevel) +
                // here appends false branch label
                label.getLabel() + ":\n" +
                this.el.codeGeneration(nestingLevel);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(cond.checkSemantics(env));

        res.addAll(th.checkSemantics(env));
        if (el != null)
            res.addAll(el.checkSemantics(env));

        return res;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}