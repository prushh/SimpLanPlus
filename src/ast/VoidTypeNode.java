package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class VoidTypeNode implements Node {

    public VoidTypeNode() {
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String toPrint(String s) {
        return s + "VoidType\n";
    }

    //non utilizzato
    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    //non utilizzato
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}  