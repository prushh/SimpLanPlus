package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class RetNode implements Node {

    private Node val;

    public RetNode(Node val) {
        this.val = val;
    }

    public String toPrint(String s) {
        return s + "Ret\n" + val.toPrint(s + "  ");
    }

    public Node typeCheck() {
        return val.typeCheck();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return val.checkSemantics(env);
    }

    public String codeGeneration() {
        return val.codeGeneration() + "ret\n";
    }

}