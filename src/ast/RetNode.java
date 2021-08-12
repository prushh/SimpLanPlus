package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class RetNode implements Node {

    private Node val;

    public RetNode(Node val) {
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
        return null;
    }

    @Override
    public Node typeCheck() {
        if (val == null)
            return new VoidTypeNode();
        else {
            Node t = val.typeCheck();
            if (t.getPointLevel() != 0) {
                System.out.println("cannot return pointers");
                System.exit(0);
            } else {
                return t;
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
        if (val != null) {
            return val.checkSemantics(env);
        }
        return new ArrayList<>();
    }


    @Override
    public int getPointLevel() {
        return 0;
    }
}