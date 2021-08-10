package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node {

    private Node exp;

    public DerExpNode(Node exp) {
        this.exp = exp;
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
        return exp.checkSemantics(env);
    }

}
