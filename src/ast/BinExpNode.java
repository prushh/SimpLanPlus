package ast;

import org.antlr.v4.runtime.Token;
import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

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
        return indent + "BinExp\n" +
                left.toPrint(indent + " ") +
                op.getText() +
                right.toPrint(indent + " ");
    }

    public Node typeCheck() {
        Node l = left.typeCheck();
        Node r = right.typeCheck();
        if (l.getPointLevel() == 0 && r.getPointLevel() == 0) {
            if (op.getText().equals("*") ||
                    op.getText().equals("/") ||
                    op.getText().equals("+") ||
                    op.getText().equals("-") ||
                    op.getText().equals("<") ||
                    op.getText().equals("<=") ||
                    op.getText().equals(">") ||
                    op.getText().equals(">=")) {
                if (!(SimpLanlib.isSubtype(l, new IntTypeNode(0))
                        && (SimpLanlib.isSubtype(r, new IntTypeNode(0))))) {
                    System.out.println("incompatible types for binary operator " + op.getText());
                    System.exit(0);
                } else {
                    return new IntTypeNode(0);
                }
            } else if (op.getText().equals("&&") ||
                    op.getText().equals("||")) {
                if (!(SimpLanlib.isSubtype(l, new BoolTypeNode(0))
                        && (SimpLanlib.isSubtype(r, new BoolTypeNode(0))))) {
                    System.out.println("incompatible types for binary operator " + op.getText());
                    System.exit(0);
                } else {
                    return new BoolTypeNode(0);
                }
            } else {
                if (!(SimpLanlib.isSubtype(l, r))) {
                    System.out.println("incompatible types for binary operator " + op.getText());
                    System.exit(0);
                } else {
                    return new BoolTypeNode(0);
                }
            }
        } else if (l.getPointLevel() != 0 && r.getPointLevel() != 0 && l.getPointLevel() == r.getPointLevel()) {
            if (op.getText().equals("==") ||
                    op.getText().equals("!=")) {
                if (!(SimpLanlib.isSubtype(l, new BoolTypeNode(0))
                        && (SimpLanlib.isSubtype(r, new BoolTypeNode(0))))) {
                    System.out.println("incompatible types for binary operator " + op.getText());
                    System.exit(0);
                } else {
                    return new BoolTypeNode(0);
                }
            }
        } else if (l.getPointLevel() != 0 && r.getPointLevel() != 0 && l.getPointLevel() != r.getPointLevel()) {
            System.out.println("can't apply 'operator " + op.getText() + "' between incompatible pointer types");
            System.exit(0);
        } else {
            System.out.println("can't apply 'operator " + op.getText() + "' between pointers and variables");
            System.exit(0);
        }


        return new NullTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
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
