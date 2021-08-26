package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import util.*;

import java.util.ArrayList;

public class NewExpNode implements Node {

    private Node type;

    public NewExpNode(Node type) {
        this.type = type;
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
        return indent + "NewExp\n" +
                type.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (SimpLanPlusLib.isSubtype(type, new IntTypeNode(0, Status.DECLARED)))
            return new IntTypeNode(type.getPointLevel() + 1, Status.DECLARED);
        else
            return new BoolTypeNode(type.getPointLevel() + 1, Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        int pointLevel = this.type.getPointLevel();

        StringBuilder builder = new StringBuilder();
        builder.append("addi $hp 1\n");
        builder.append("si -1 $hp\n");
        for (int idx = 0; idx < pointLevel; idx++) {
            builder.append("lhp\n");
            builder.append("addi $hp 1\n");
            builder.append("sw $a0 $hp\n");
        }
        builder.append("lhp\n");
        return builder.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
