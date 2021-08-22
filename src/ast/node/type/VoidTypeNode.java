package ast.node.type;

import ast.Node;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public class VoidTypeNode implements Node {

    private Status status;

    public VoidTypeNode(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "VoidType\n";
    }

    //non utilizzato
    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    //non utilizzato
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}  