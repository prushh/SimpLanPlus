package ast;

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
        return null;
    }

    @Override
    public Node typeCheck() {
        return new IntTypeNode(0);
    }

    @Override
    public String codeGeneration() {
        return null;
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