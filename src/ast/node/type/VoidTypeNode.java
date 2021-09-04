package ast.node.type;

import ast.Node;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * VoidType for function that doesn't have return value.
 */

public class VoidTypeNode implements Node {

    private Status status;

    public VoidTypeNode(Status status) {
        this.status = status;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String toPrint(String indent) {
        return indent + "VoidType\n";
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return null;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

}