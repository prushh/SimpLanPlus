package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;

public class NewExpNode implements Node {

    private Node type;

    public NewExpNode(Node type) {
        this.type = type;
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
        return null;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (SimpLanlib.isSubtype(type, new IntTypeNode(0, Status.DECLARED)))
            return new IntTypeNode(type.getPointLevel() + 1, Status.DECLARED);
        else
            return new BoolTypeNode(type.getPointLevel() + 1, Status.DECLARED);
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
