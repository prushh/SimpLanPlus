package ast.node.exp;

import ast.Node;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Base expressions node.
 *
 * exp	    : '(' exp ')'		 #baseExp
 */

public class BaseExpNode implements Node {

    private Node exp;

    public BaseExpNode(Node exp) {
        this.exp = exp;
    }

    // Getter needed when we have nested parentheses to get the expression inside
    public Node getExp() {
        return this.exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
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
    public String toPrint(String indent) {
        return indent + "BaseExp\n" + exp.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.exp.codeGeneration(env);
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
