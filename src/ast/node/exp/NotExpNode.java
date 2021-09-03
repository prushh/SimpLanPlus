package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Negation (operation for booleans) expression node.
 *
 * exp    :    '!' exp    #notExp
 */

public class NotExpNode implements Node {

    private Node exp;

    public NotExpNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node t = this.exp.typeCheck(typeErr);
        if (!SimpLanPlusLib.isSubtype(t, new BoolTypeNode(0, Status.DECLARED)) || t.getPointLevel() != 0) {
            typeErr.add(new SemanticError("incompatible types for operator !"));
        }
        return t;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return exp.checkEffects(env);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "NotExp\n" + exp.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.exp.codeGeneration(env) + "li $t0 1\n" + "sub $t0 $a0 $a0\n";
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
