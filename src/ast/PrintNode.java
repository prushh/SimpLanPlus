package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class PrintNode implements Node {

    private Node val;

    public PrintNode(Node val) {
        this.val = val;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        this.val.typeCheck();
        return new NullTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return val.checkSemantics(env);
    }
}