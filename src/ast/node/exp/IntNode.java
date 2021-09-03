package ast.node.exp;

import ast.Node;
import ast.node.type.IntTypeNode;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Integer expression node.
 *
 * exp    :    INT    #valExp
 */

public class IntNode implements Node {

    private int val;

    public IntNode(int val) {
        this.val = val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return new IntTypeNode(0, Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String toPrint(String indent) {
        return indent + "ValExp: " + val + "\n";
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return "li $a0 " + val + "\n";
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