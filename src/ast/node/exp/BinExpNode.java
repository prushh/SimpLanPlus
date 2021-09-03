package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import org.antlr.v4.runtime.Token;
import util.*;

import java.util.ArrayList;

/**
 * Binary expressions node.
 *
 * exp    :    left=exp    op    right=exp    #binExp
 */

public class BinExpNode implements Node {

    private Node left;
    private Node right;
    private Token op;

    public BinExpNode(Node left, Token op, Node right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(left.checkSemantics(env));
        res.addAll(right.checkSemantics(env));

        return res;
    }

    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node l = left.typeCheck(typeErr);
        Node r = right.typeCheck(typeErr);

        // First we distinguish between normal variables and pointer variables
        if (l.getPointLevel() == 0 && r.getPointLevel() == 0) {
            // For normal variables we distinguish between operations on integer and booleans
            if (op.getText().equals("*") || op.getText().equals("/") || op.getText().equals("+")
                    || op.getText().equals("-")) {
                // Integer operations type checking (e.g 1 + 1 must be typed as an integer exp)
                if (!(SimpLanPlusLib.isSubtype(l, new IntTypeNode(0, Status.DECLARED))
                        && (SimpLanPlusLib.isSubtype(r, new IntTypeNode(0, Status.DECLARED))))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));
                } else {
                    return new IntTypeNode(0, Status.DECLARED);
                }
            } else if (op.getText().equals("<") || op.getText().equals("<=") || op.getText().equals(">")
                    || op.getText().equals(">=")) {
                // Example: 2 < 3 is typed as a boolean expression
                if (!(SimpLanPlusLib.isSubtype(l, new IntTypeNode(0, Status.DECLARED))
                        && (SimpLanPlusLib.isSubtype(r, new IntTypeNode(0, Status.DECLARED))))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));

                } else {
                    return new BoolTypeNode(0, Status.DECLARED);
                }
            } else if (op.getText().equals("&&") || op.getText().equals("||")) {
                // Booleans operations type checking (e.g. true || false)
                if (!(SimpLanPlusLib.isSubtype(l, new BoolTypeNode(0, Status.DECLARED))
                        && (SimpLanPlusLib.isSubtype(r, new BoolTypeNode(0, Status.DECLARED))))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));
                } else {
                    return new BoolTypeNode(0, Status.DECLARED);
                }
            } else {
                if (!(SimpLanPlusLib.isSubtype(l, r))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));
                } else {
                    return new BoolTypeNode(0, Status.DECLARED);
                }
            }
        } else if (l.getPointLevel() != 0 && r.getPointLevel() != 0 && l.getPointLevel() == r.getPointLevel()) {
            // For pointers we consider only operators == and !=
            if (op.getText().equals("==") || op.getText().equals("!=")) {
                if (!(SimpLanPlusLib.isSubtype(l, r))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));
                } else {
                    return new BoolTypeNode(0, Status.DECLARED);
                }
            }
        } else if (l.getPointLevel() != 0 && r.getPointLevel() != 0 && l.getPointLevel() != r.getPointLevel()) {
            // Left-hand-side and right-hand-side of the binary operator are two pointers with different point level
            typeErr.add(
                    new SemanticError("can't apply operator '" + op.getText() + "' between pointers and variables"));
        } else {
            typeErr.add(
                    new SemanticError("can't apply operator '" + op.getText() + "' between pointers and variables"));
        }

        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(left.checkEffects(env));
        res.addAll(right.checkEffects(env));

        return res;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "BinExp[" + op.getText() + "]\n" + left.toPrint(indent + "\t") + right.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        builder.append(left.codeGeneration(env))
                .append("push $a0\n")
                .append(right.codeGeneration(env))
                .append("lw $t0 $sp\n");
        switch (op.getText()) {
            case "+":
                builder.append("add $t0 $a0 $a0\n");
                break;
            case "-":
                builder.append("sub $t0 $a0 $a0\n");
                break;
            case "/":
                builder.append("div $t0 $a0 $a0\n");
                break;
            case "*":
                builder.append("mult $t0 $a0 $a0\n");
                break;
            case "<":
                // $a0 < $t0
                builder.append("less $t0 $a0 $a0\n");
                break;
            case "<=":
                // $a0 <= $t0
                builder.append("leq $t0 $a0 $a0\n");
                break;
            case ">":
                // $t0 < $a0
                builder.append("less $a0 $t0 $a0\n");
                break;
            case ">=":
                // $t0 <= $a0
                builder.append("leq $a0 $t0 $a0\n");
                break;
            case "==":
                // $a0 == $t0
                builder.append("eq $t0 $a0 $a0\n");
                break;
            case "!=":
                // $a0 == $t0
                builder.append("neq $t0 $a0 $a0\n");
                break;
            case "&&":
                // $a0 && $t0
                builder.append("and $t0 $a0 $a0\n");
                break;
            case "||":
                // $a0 || $t0
                builder.append("or $t0 $a0 $a0\n");
                break;
            default:
        }

        builder.append("pop\n");

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
