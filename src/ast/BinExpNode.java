package ast;

import org.antlr.v4.runtime.Token;
import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

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
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        Node l = left.typeCheck();
        Node r = right.typeCheck();
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

}
