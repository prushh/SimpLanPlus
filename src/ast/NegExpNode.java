package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;
import util.Status;

import java.util.ArrayList;

public class NegExpNode implements Node {

    private Node exp;

    public NegExpNode(Node exp) {
        this.exp = exp;
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
        Node t = this.exp.typeCheck(typeErr);
        if (!SimpLanPlusLib.isSubtype(t, new IntTypeNode(0, Status.DECLARED)) || t.getPointLevel() != 0) {
            typeErr.add(new SemanticError("incompatible types for operator -"));
        }
        return t;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return exp.checkEffects(env);
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
