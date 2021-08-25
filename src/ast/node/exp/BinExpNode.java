package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import org.antlr.v4.runtime.Token;
import util.*;

import java.util.ArrayList;

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
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String toPrint(String indent) {
        return indent + "BinExp[" + op.getText() + "]\n" +
                left.toPrint(indent + "\t") +
                right.toPrint(indent + "\t");
    }

    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node l = left.typeCheck(typeErr);
        Node r = right.typeCheck(typeErr);
        if (l.getPointLevel() == 0 && r.getPointLevel() == 0) {
            if (op.getText().equals("*") ||
                    op.getText().equals("/") ||
                    op.getText().equals("+") ||
                    op.getText().equals("-")) {
                if (!(SimpLanPlusLib.isSubtype(l, new IntTypeNode(0, Status.DECLARED))
                        && (SimpLanPlusLib.isSubtype(r, new IntTypeNode(0, Status.DECLARED))))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));
                } else {
                    return new IntTypeNode(0, Status.DECLARED);
                }
            } else if (op.getText().equals("<") ||
                    op.getText().equals("<=") ||
                    op.getText().equals(">") ||
                    op.getText().equals(">=")) {

                if (!(SimpLanPlusLib.isSubtype(l, new IntTypeNode(0, Status.DECLARED))
                        && (SimpLanPlusLib.isSubtype(r, new IntTypeNode(0, Status.DECLARED))))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));

                } else {
                    return new BoolTypeNode(0, Status.DECLARED);
                }
            } else if (op.getText().equals("&&") ||
                    op.getText().equals("||")) {
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
            if (op.getText().equals("==") ||
                    op.getText().equals("!=")) {
                if (!(SimpLanPlusLib.isSubtype(l, r))) {
                    typeErr.add(new SemanticError("incompatible types for binary operator " + op.getText()));
                } else {
                    return new BoolTypeNode(0, Status.DECLARED);
                }
            }
        } else if (l.getPointLevel() != 0 && r.getPointLevel() != 0 && l.getPointLevel() != r.getPointLevel()) {
            typeErr.add(new SemanticError("can't apply operator '" + op.getText() + "' between pointers and variables"));
        } else {
            typeErr.add(new SemanticError("can't apply operator '" + op.getText() + "' between pointers and variables"));
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
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        builder.append(left.codeGeneration(env));
        builder.append("push $a0\n");
        builder.append(right.codeGeneration(env));
        builder.append("lw $t0 $sp\n");
        switch (op.getText()) {
            case "+":
                builder.append("add $a0 $t0 $a0\n");
                break;
            case "-":
                builder.append("sub $a0 $t0 $a0\n");
                break;
            case "/":
                builder.append("div $a0 $t0 $a0\n");
                break;
            case "*":
                builder.append("mult $a0 $t0 $a0\n");
                break;
            case "<":
                // $a0 < $t0
                builder.append("less $a0 $t0 $a0\n");
                break;
            case "<=":
                // $a0 <= $t0
                builder.append("leq $a0 $t0 $a0\n");
                break;
            case ">":
                // $t0 < $a0
                builder.append("leq $t0 $a0 $a0\n");
                break;
            case ">=":
                // $t0 <= $a0
                builder.append("less $t0 $a0 $a0\n");
                break;
            case "==":
                // $a0 == $t0
                builder.append("eq $a0 $t0 $a0\n");
                break;
            case "!=":
                // $a0 == $t0
                builder.append("neq $a0 $t0 $a0\n");
                break;
            case "&&":
                // $a0 && $t0
                builder.append("and $a0 $t0 $a0\n");
                break;
            case "||":
                // $a0 || $t0
                builder.append("or $a0 $t0 $a0\n");
                break;
            default:
        }

        builder.append("pop\n");

        return builder.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(left.checkSemantics(env));
        res.addAll(right.checkSemantics(env));

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
