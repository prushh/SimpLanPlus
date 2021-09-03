package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.exp.BaseExpNode;
import ast.node.exp.NewExpNode;
import ast.node.other.LhsNode;
import ast.node.type.NullTypeNode;
import util.*;


import java.util.ArrayList;
import java.util.Objects;


/**
 * Assignment statement node.
 * <p>
 * assignment : lhs '=' exp ;
 */

public class AsgNode implements Node {

    private LhsNode lhs;
    private Node exp;

    public AsgNode(LhsNode lhs, Node exp) {
        this.lhs = lhs;
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        /*
         * Before checking lhs, we check expression because it can be a function that
         * modifies global variables through pointers
         */
        res.addAll(exp.checkSemantics(env));
        res.addAll(lhs.checkSemantics(env));

        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node exp = this.exp.typeCheck(typeErr);
        Node lhs = this.lhs.typeCheck(typeErr);
        if (!(SimpLanPlusLib.isSubtype(lhs, exp))) {
            typeErr.add(new SemanticError("incompatible value for variable " + this.lhs.getID()));
        } else {
            if (!Objects.equals(lhs.getPointLevel(), exp.getPointLevel())) {
                typeErr.add(new SemanticError("cannot assign variable or pointers of different type"));
            }
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;

        while (idLevel >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(idLevel--)).get(lhs.getID());
        }
        Node tmp = this.exp;
        /*
         *  Similar case of CallNode, from base expressions we are interested in
         *  getting expression nested inside parentheses
         */
        if (exp instanceof BaseExpNode) {
            while (((BaseExpNode) tmp).getExp() instanceof BaseExpNode) {
                tmp = ((BaseExpNode) tmp).getExp();
            }
            tmp = ((BaseExpNode) tmp).getExp();
        }
        if (tmp instanceof NewExpNode) {
            tmpEntry.getType().setStatus(Status.READWRITE);
        } else {
            res.addAll(exp.checkEffects(env));
            res.addAll(lhs.checkEffects(env));
            Status newlhsAsg = Status.READWRITE;
            newlhsAsg = SimpLanPlusLib.seqStatus(tmpEntry.getType().getStatus(), newlhsAsg);
            tmpEntry.getType().setStatus(newlhsAsg);
        }
        // Update symbol table
        env.symTable.get(tmpEntry.getNestinglevel()).replace(lhs.getID(), tmpEntry);


        return res;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Assignment\n" +
                lhs.toPrint(indent + "\t") +
                exp.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {

        return exp.codeGeneration(env) +
                "push $a0\n" +
                lhs.codeGeneration(env) +
                "lw $t0 $sp\n" +
                "pop\n" +
                "sw $t0 $a0\n";
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
