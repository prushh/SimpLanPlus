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
    public Node typeCheck() {
        if (entry.getType() instanceof ArrowTypeNode) {
            System.out.println("wrong usage of function identifier");
            System.exit(0);
        }
        int difference = entry.getType().getPointLevel() - this.pointLevel;
        if (difference < 0) {
            System.out.println("too many dereferencing operations");
            System.exit(0);
        }
        Node type;
        if (SimpLanlib.isSubtype(entry.getType(), new IntTypeNode(0)))
            type = new IntTypeNode(difference);
        else
            type = new BoolTypeNode(difference);

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