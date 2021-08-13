package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;

public class LhsNode implements Node {

    private String ID;
    private STentry entry;
    private int pointLevel;

    public LhsNode(String ID, int pointLevel) {
        this.ID = ID;
        this.pointLevel = pointLevel;
    }

    // TODO
    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (entry.getType() instanceof ArrowTypeNode) {
            typeErr.add(new SemanticError("wrong usage of function identifier"));
        }
        int difference = entry.getType().getPointLevel() - this.pointLevel;
        if (difference < 0) {
            typeErr.add(new SemanticError("too many dereferencing operations"));
        }
        Node type;
        if (SimpLanlib.isSubtype(entry.getType(), new IntTypeNode(0, Status.DECLARED)))
            type = new IntTypeNode(difference, Status.DECLARED);
        else
            type = new BoolTypeNode(difference, Status.DECLARED);

        return type;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int i = env.nestingLevel;
        STentry tmpEntry = null;

        while (i >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(i--)).get(ID);
        }

        if (tmpEntry == null) {
            res.add(new SemanticError("Id " + ID + " not declared"));
        } else {
            entry = tmpEntry;
        }

        return res;
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }

}