package ast.node.exp;

import ast.Node;
import ast.node.type.IntTypeNode;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class IntNode implements Node {

    private int val;

    public IntNode(int val) {
        this.val = val;
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
        return indent + "ValExp: " + val + "\n";
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
    public String codeGeneration(int nestingLevel) {
        return "li $a0 " + val + "\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }


    @Override
    public int getPointLevel() {
        return 0;
    }
}