package ast.node.statement;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Print statement node.
 *
 * print    :    'print' exp;
 */

public class PrintNode implements Node {

    private Node val;

    public PrintNode(Node val) {
        this.val = val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return val.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node t = this.val.typeCheck(typeErr);
        if ((!SimpLanPlusLib.isSubtype(t, new IntTypeNode(0, Status.DECLARED))
                && !SimpLanPlusLib.isSubtype(t, new BoolTypeNode(0, Status.DECLARED)))
            // Decomment this line if you want not to be able to print pointer values
            // || (t.getPointLevel() != 0)
        ) {
            typeErr.add(new SemanticError("incompatible type for print"));
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return val.checkEffects(env);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Print\n" + val.toPrint(indent + "\t");
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.val.codeGeneration(env) + "print $a0\n";
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