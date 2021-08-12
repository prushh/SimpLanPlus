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
    public Node typeCheck() {
        Node cond_type = cond.typeCheck();
        if (cond_type.getPointLevel() != 0 || !(SimpLanlib.isSubtype(cond_type, new BoolTypeNode(0, Status.DECLARED)))) {
            System.out.println("non boolean condition in if");
            System.exit(0);
        }
        Node t = th.typeCheck();
        if (el != null) {
            Node e = el.typeCheck();
            if (SimpLanlib.isSubtype(t, e))
                return e;
            System.out.println("Incompatible types in then else branches");
            System.exit(0);
        }
        return t;
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