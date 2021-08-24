package ast.node.exp;

import ast.Node;
import ast.node.type.IntTypeNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;
import util.Status;

import java.util.ArrayList;

public class NegExpNode implements Node {

    private Node exp;

    public NegExpNode(Node exp) {
        this.exp = exp;
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
        return indent + "NegExp\n" + exp.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node t = this.exp.typeCheck(typeErr);
        if (!SimpLanPlusLib.isSubtype(t, new IntTypeNode(0, Status.DECLARED)) || t.getPointLevel() != 0) {
            typeErr.add(new SemanticError("incompatible types for operator -"));
        }
        return t;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return exp.checkEffects(env);
    }

    @Override
    public String codeGeneration(int nestingLevel) {
        // per trovare il negativo si sottrae l'intero con 0
        return this.exp.codeGeneration(nestingLevel) +
                "li $t0 0\n" +
                "sub $t0 $a0 $a0\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
