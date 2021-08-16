package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;

public class IteNode implements Node {

    private Node cond;
    private Node th;
    private Node el;

    public IteNode(Node cond, Node th, Node el) {
        this.cond = cond;
        this.th = th;
        this.el = el;
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
        Node cond_type = cond.typeCheck(typeErr);
        if (cond_type.getPointLevel() != 0 || !(SimpLanlib.isSubtype(cond_type, new BoolTypeNode(0, Status.DECLARED)))) {
            typeErr.add(new SemanticError("non boolean condition in if"));
        }
        Node t = th.typeCheck(typeErr);
        if (el != null) {
            Node e = el.typeCheck(typeErr);
            if (SimpLanlib.isSubtype(t, e))
                return e;
            typeErr.add(new SemanticError("Incompatible types in then else branches"));
        }
        return t;
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
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(cond.checkSemantics(env));
        res.addAll(th.checkSemantics(env));
        if (el != null)
            res.addAll(el.checkSemantics(env));

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }
}