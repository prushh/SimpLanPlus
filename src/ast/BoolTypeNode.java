package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class BoolTypeNode implements Node {

    private Integer pointLevel;

    public BoolTypeNode(Integer pointLevel) {
        this.pointLevel = pointLevel;
    }

    public String toPrint(String s) {
        return s + "BoolType\n";
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
}