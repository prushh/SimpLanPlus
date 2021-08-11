package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class NegExpNode implements Node {

    private Node exp;

    public NegExpNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        Node t = this.exp.typeCheck();
        if (!SimpLanlib.isSubtype(t,new IntTypeNode(0)) || t.getPointLevel() != 0){
            System.out.println("incompatible types for operator -");
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
        return exp.checkSemantics(env);
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }


}
