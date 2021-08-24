package ast.node.exp;

import ast.Node;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class BaseExpNode implements Node {

    private Node exp;

    public BaseExpNode(Node exp) {
        this.exp = exp;
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
        return indent + "BaseExp\n" +
                exp.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.exp.typeCheck(typeErr);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return exp.checkEffects(env);
    }

    @Override
    public String codeGeneration(int nestingLevel) {
        return this.exp.codeGeneration(nestingLevel);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Add res to _ExpNode? (neg, not, etc.)
        return exp.checkSemantics(env);
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    public Node getExp() {
        return this.exp;
    }

}
