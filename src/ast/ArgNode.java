package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class ArgNode implements Node {

    private String ID;
    private Node type;

    public ArgNode(String ID, Node type) {
        this.ID = ID;
        this.type = type;
    }

    public String getId() {
        return ID;
    }

    public Node getType() {
        return type;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck () {
        return this.type;
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
    public Integer getPointLevel() {
        return 0;
    }

}