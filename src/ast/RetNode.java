package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class RetNode implements Node {

    private Node val;

    public RetNode(Node val) {
        this.val = val;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        if (val == null)
            return new VoidTypeNode();
        else
            return val.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if (val != null) {
            return val.checkSemantics(env);
        }
        return new ArrayList<>();
    }
}