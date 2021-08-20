package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class ArrowTypeNode implements Node {

    private ArrayList<ArgNode> args;
    private Node ret;

    public ArrowTypeNode(ArrayList<ArgNode> args, Node ret) {
        this.args = args;
        this.ret = ret;
    }

    public Node getRet() { //
        return ret;
    }

    public ArrayList<ArgNode> getArgList() { //
        return args;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        for (ArgNode arg : args)
            builder.append(arg.toPrint(indent + "\t"));
        return indent + "ArrowType\n" +
                builder +
                ret.toPrint(indent + " -> ");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
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