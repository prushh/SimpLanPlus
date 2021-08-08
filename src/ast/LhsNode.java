package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class LhsNode implements Node {

    private String ID;
    private STentry entry;
    private Integer pointLevel;

    public LhsNode(String ID, Integer pointLevel) {
        this.ID = ID;
        this.pointLevel = pointLevel;
    }

    @Override
    public String toPrint(String indent) {
        return null;
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
}