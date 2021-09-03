package ast.node.exp;

import ast.Node;
import ast.node.other.LhsNode;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Right-hand-side expression node.
 *
 * exp    :    lhs    #derExp
 */

public class DerExpNode implements Node {

    private LhsNode lhs;

    public DerExpNode(LhsNode exp) {
        this.lhs = exp;
    }

    public LhsNode getLhsNode() {
        return this.lhs;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return lhs.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return lhs.typeCheck(typeErr);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return lhs.checkEffects(env);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "DerExp\n" + lhs.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.lhs.codeGeneration(env);
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {
    }

}
