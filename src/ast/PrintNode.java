package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;
import util.Status;

import java.util.ArrayList;

public class PrintNode implements Node {

    private Node val;

    public PrintNode(Node val) {
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
        return indent + "Print\n" + val.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node t = this.val.typeCheck(typeErr);
        if ((!SimpLanPlusLib.isSubtype(t, new IntTypeNode(0, Status.DECLARED)) && !SimpLanPlusLib.isSubtype(t, new BoolTypeNode(0, Status.DECLARED)))
        //  Decomment this line if you want not to be able to print pointer values
            //        || (t.getPointLevel() != 0)
        ) {
            typeErr.add(new SemanticError("incompatible type for print"));
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return val.checkEffects(env);
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
    public int getPointLevel() {
        return 0;
    }
}