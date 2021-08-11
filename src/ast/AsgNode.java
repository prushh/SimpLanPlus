package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class AsgNode implements Node {

    private LhsNode lhs;
    private Node exp;

    public AsgNode(LhsNode lhs, Node exp) {
        this.lhs = lhs;
        this.exp = exp;
    }

    @Override
    public String toPrint(String s) {
        return null;
    }

    @Override
    public Node typeCheck() {
        Node l = this.lhs.typeCheck();
        Node r = this.exp.typeCheck();
        if (!(SimpLanlib.isSubtype(l, r))) {
            System.out.println("incompatible value for variable " + this.lhs);
            System.exit(0);
        } else {
            if (l.getPointLevel() != r.getPointLevel()) {
                System.out.println("cannot assign variable or pointers of different type");
                System.exit(0);
            }
        }
        return new NullTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(lhs.checkSemantics(env));
        if (exp != null) {
            res.addAll(exp.checkSemantics(env));
        }

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
