package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class BoolNode implements Node {

    private boolean val;

    public BoolNode(boolean val) {
        this.val = val;
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
        if (val)
            return indent + "Bool: true\n";
        return indent + "Bool: false\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return new BoolTypeNode(0, Status.DECLARED);
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