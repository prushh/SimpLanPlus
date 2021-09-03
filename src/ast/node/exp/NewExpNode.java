package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Pointers allocation expression node.
 *
 * exp    :    'new' type    #newExp
 */

public class NewExpNode implements Node {

    private Node type;

    public NewExpNode(Node type) {
        this.type = type;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
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
    public String toPrint(String indent) {
        return indent + "NewExp\n" + type.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        int pointLevel = this.type.getPointLevel();

        StringBuilder builder = new StringBuilder();
        // Increment heap pointer and store a default value
        builder.append("addi $hp 1\n")
                .append("si 0 $hp\n");
        for (int idx = 0; idx < pointLevel; idx++) {
            // Allocate a number of heap memory cell equal to the point level of new expression
            builder.append("lhp\n")
                    .append("addi $hp 1\n")
                    // Store in the new memory cell the adress of the old value of the heap pointer
                    .append("sw $a0 $hp\n");
        }
        builder.append("lhp\n");
        return builder.toString();
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
