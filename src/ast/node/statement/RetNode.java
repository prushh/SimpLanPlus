package ast.node.statement;

import ast.Node;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Return statement node.
 *
 * ret    :    'return' (exp)?;
 */

public class RetNode implements Node {

    private Node val;

    private boolean isFunctionReturn;

    public boolean isFunctionReturn() {
        return isFunctionReturn;
    }

    public void setFunctionReturn(boolean functionReturn) {
        isFunctionReturn = functionReturn;
    }

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
            return indent + "Return\n" + val.toPrint(indent + "\t");
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
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        String exp = "";
        if (this.val != null) {
            exp = this.val.codeGeneration(env) + "srv\n"; // Store $a0 into $rv
        }
        String label = env.getLabel();
        builder.append(exp);
        builder.append("b " + label + "\n"); // Jump to the end of the function/block body

        return builder.toString();
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