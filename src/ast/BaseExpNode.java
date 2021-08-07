package ast;

import org.antlr.v4.runtime.Token;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BaseExpNode implements Node {

    private Node exp;

    public BaseExpNode(Node exp) {
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
        return null;
    }

}
