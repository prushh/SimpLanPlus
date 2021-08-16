package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;
import java.util.Objects;

public class AsgNode implements Node {

    private LhsNode lhs;
    private Node exp;

    public AsgNode(LhsNode lhs, Node exp) {
        this.lhs = lhs;
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
        return indent + "Asg\n" +
                lhs.toPrint(indent + " ") +
                exp.toPrint(indent + " ");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node lhs = this.lhs.typeCheck(typeErr);
        Node exp = this.exp.typeCheck(typeErr);
        if (!(SimpLanlib.isSubtype(lhs, exp))) {
            typeErr.add(new SemanticError("incompatible value for variable " + this.lhs));
        } else {
            if (!Objects.equals(lhs.getPointLevel(), exp.getPointLevel())) {
                typeErr.add(new SemanticError("cannot assign variable or pointers of different type"));
            }
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(lhs.checkSemantics(env));
        if (exp != null) {
            res.addAll(exp.checkSemantics(env));
        }

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
