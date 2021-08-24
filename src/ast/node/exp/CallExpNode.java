package ast.node.exp;

import ast.Node;
import ast.node.statement.CallNode;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class CallExpNode implements Node {

    private CallNode callNode;

    public CallExpNode(CallNode callNode) {
        this.callNode = callNode;
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
        return indent + "CallExp\n" +
                callNode.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.callNode.typeCheck(typeErr);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return this.callNode.checkEffects(env);
    }

    @Override
    public String codeGeneration() {
        return this.callNode.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return callNode.checkSemantics(env);
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
