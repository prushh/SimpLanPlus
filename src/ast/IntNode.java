package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class IntNode implements Node {

    private Integer val;

    public IntNode(Integer val) {
        this.val = val;
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