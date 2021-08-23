package ast.node.statement;

import ast.Node;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
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
        if (val != null) {
            return indent + "Return\n" +
                    val.toPrint(indent + "\t");
        }
        return indent + "Return\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (val == null)
            return new VoidTypeNode(Status.DECLARED);
        else {
            Node t = val.typeCheck(typeErr);
            if (t.getPointLevel() != 0) {
                typeErr.add(new SemanticError("cannot return pointers"));
            } else {
                return t;
            }
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        if (val != null) {
            return val.checkEffects(env);
        }
        return new ArrayList<>();
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