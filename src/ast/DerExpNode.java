package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class DerExpNode implements Node {

    private LhsNode lhs;

    public DerExpNode(LhsNode exp) {
        this.lhs = exp;
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
        return lhs.typeCheck(typeErr);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return lhs.checkEffects(env);
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return lhs.checkSemantics(env);
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

    public LhsNode getLhsNode(){
        return this.lhs;
    }

}
