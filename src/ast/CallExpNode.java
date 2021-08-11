package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class CallExpNode implements Node {

    private Node callNode;

    public CallExpNode(Node callNode) {
        this.callNode = callNode;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        return this.callNode.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return callNode.checkSemantics(env);
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }




}
