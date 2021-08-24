package ast.node.type;

import ast.Node;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class IntTypeNode implements Node {

    private int pointLevel;
    private Status status;

    public IntTypeNode(int pointLevel, Status status) {
        this.pointLevel = pointLevel;
        this.status = status;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pointLevel; i++) {
            builder.append("^");
        }
        return indent + builder + "IntType\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
    }

    @Override
    public String codeGeneration(int nestingLevel) {
        return "";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

}