package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

public class IteNode implements Node {

    private Node cond;
    private Node th;
    private Node el;

    public IteNode(Node cond, Node th, Node el) {
        this.cond = cond;
        this.th = th;
        this.el = el;
    }

    public String toPrint(String s) {
        return s + "If\n" + cond.toPrint(s + "  ")
                + th.toPrint(s + "  ")
                + el.toPrint(s + "  ");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create the result
        ArrayList<SemanticError> res = new ArrayList<>();

        //check semantics in the condition
        res.addAll(cond.checkSemantics(env));

        //check semantics in the then and in the else exp
        res.addAll(th.checkSemantics(env));
        res.addAll(el.checkSemantics(env));

        return res;
    }

    public Node typeCheck() {
        if (!(SimpLanlib.isSubtype(cond.typeCheck(), new BoolTypeNode(0)))) {
            System.out.println("non boolean condition in if");
            System.exit(0);
        }
        Node t = th.typeCheck();
        Node e = el.typeCheck();
        if (SimpLanlib.isSubtype(t, e))
            return e;
        if (SimpLanlib.isSubtype(e, t))
            return t;
        System.out.println("Incompatible types in then else branches");
        System.exit(0);
        return null;
    }

    public String codeGeneration() {
        String l1 = SimpLanlib.freshLabel();
        String l2 = SimpLanlib.freshLabel();
        return cond.codeGeneration() +
                "push 1\n" +
                "beq " + l1 + "\n" +
                el.codeGeneration() +
                "b " + l2 + "\n" +
                l1 + ":\n" +
                th.codeGeneration() +
                l2 + ":\n";
    }

}