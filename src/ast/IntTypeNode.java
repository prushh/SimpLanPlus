package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class IntTypeNode implements Node {

    private Integer pointLevel;
    private Status status = Status.DECLARED;

    public IntTypeNode(Integer pointLevel) {
        this.pointLevel = pointLevel;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toPrint(String s) {
        return s + "IntType\n";
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }
}