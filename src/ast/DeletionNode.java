package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeletionNode implements Node {

    private String ID;

    public DeletionNode(String ID) {
        this.ID = ID;
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
