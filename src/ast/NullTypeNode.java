package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class NullTypeNode implements Node {

    public NullTypeNode() {
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    public String toPrint(String s) {
        return s + "NullType\n";
    }

    //non utilizzato
    public Node typeCheck() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    //non utilizzato
    public String codeGeneration() {
        return "";
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}  