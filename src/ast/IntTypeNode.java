package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class IntTypeNode implements Node {

    private Integer pointLevel;

    public IntTypeNode(Integer pointLevel) {
        this.pointLevel = pointLevel;
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
    public Integer getPointLevel() {
        return this.pointLevel;
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }
}