package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

public class PrintNode implements Node {

    private Node val;

    public PrintNode(Node val) {
        this.val = val;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        Node t = this.val.typeCheck();
        if ((!SimpLanlib.isSubtype(t,new IntTypeNode(0)) && !SimpLanlib.isSubtype(t,new BoolTypeNode(0)))
             || (t.getPointLevel() != 0))
            {
                System.out.println("incompatible type for print");
                System.exit(0);
            }
        return new NullTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return val.checkSemantics(env);
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }

}