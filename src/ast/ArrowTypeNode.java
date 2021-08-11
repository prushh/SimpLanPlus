package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class ArrowTypeNode implements Node {

    private ArrayList<Node> args;
    private Node ret;

    public ArrowTypeNode(ArrayList<Node> args, Node ret) {
        this.args = args;
        this.ret = ret;
    }

    public Node getRet() { //
        return ret;
    }

    public ArrayList<Node> getParList() { //
        return args;
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

    @Override
    public Integer getPointLevel() {
        return 0;
    }

}