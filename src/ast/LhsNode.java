package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class LhsNode implements Node {

    private String id;
    private STentry entry;
    private int nestinglevel;

    public LhsNode(String id) {
        this.id = id;
    }

    @Override
    public String toPrint(String s) {
        return s + "Id:" + id + " at nestlev " + nestinglevel + "\n" + entry.toPrint(s + "  ");
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