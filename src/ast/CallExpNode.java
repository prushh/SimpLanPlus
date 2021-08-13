package ast;

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
        return null;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.callNode.typeCheck(typeErr);
    }

    @Override
    public String codeGeneration() {
        return null;
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
