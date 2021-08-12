package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class NullTypeNode implements Node {

    private Status status;

    public NullTypeNode(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
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