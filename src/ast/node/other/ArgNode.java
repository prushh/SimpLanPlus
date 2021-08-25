package ast.node.other;

import ast.Node;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

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
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String toPrint(String indent) {
        return indent + "Arg\n" +
                type.toPrint(indent + "\t") + "\t\t" +
                ID +
                "\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.type.typeCheck(typeErr);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
    }

    @Override
    public String codeGeneration(CGenEnv env) {
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