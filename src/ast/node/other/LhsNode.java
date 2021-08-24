package ast.node.other;

import ast.Node;
import ast.STentry;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;
import util.Status;

import java.util.ArrayList;

public class LhsNode implements Node {

    private String ID;
    private STentry entry;
    private int pointLevel;
    private boolean isRightHandSide = false;

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
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pointLevel; i++) {
            builder.append("^");
        }
        String str = "\n" + indent + "at nesting level " + this.entry.getNestinglevel() +
                " with offset " + this.entry.getOffset();
        builder.append(str);
        return indent + this.ID + builder + "\n";
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
        if (SimpLanPlusLib.isSubtype(entry.getType(), new IntTypeNode(0, Status.DECLARED)))
            type = new IntTypeNode(difference, Status.DECLARED);
        else
            type = new BoolTypeNode(difference, Status.DECLARED);

        return type;
    }


    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;

        while (idLevel >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(idLevel--)).get(ID);
        }

        if (isRightHandSide) {

            int difference = entry.getType().getPointLevel() - this.pointLevel;

            if (difference == 0) {

                if (tmpEntry.getType().getStatus() != Status.READWRITE) {

                    if (tmpEntry.getType().getStatus() == Status.DECLARED) {
                        res.add(new SemanticError("Id --" + ID + "-- has not been initialized"));
                    } else if (tmpEntry.getType().getStatus() == Status.DELETED) {
                        res.add(new SemanticError("Id --" + ID + "-- has been deleted"));
                    } else if (tmpEntry.getType().getStatus() == Status.ERROR) {
                        res.add(new SemanticError("Id --" + ID + "-- has returned an error status"));
                    }

                    tmpEntry.getType().setStatus(Status.ERROR);
                    env.symTable.get(tmpEntry.getNestinglevel()).replace(this.ID, tmpEntry);

                }
            } else {

                if (tmpEntry.getType().getStatus().ordinal() != Status.READWRITE.ordinal()) {

                    if (tmpEntry.getType().getStatus() == Status.DECLARED) {
                        res.add(new SemanticError("Pointer --" + ID + "-- has not been initialized"));
                    } else if (tmpEntry.getType().getStatus() == Status.DELETED) {
                        res.add(new SemanticError("Pointer --" + ID + "-- has been deleted"));
                    } else if (tmpEntry.getType().getStatus() == Status.ERROR) {
                        res.add(new SemanticError("Pointer --" + ID + "-- has returned an error status"));
                    }

                    tmpEntry.getType().setStatus(Status.ERROR);
                    env.symTable.get(tmpEntry.getNestinglevel()).replace(this.ID, tmpEntry);

                }
            }
        } else {
            if (tmpEntry.getType().getStatus() != Status.READWRITE &&
                    (this.pointLevel > 0) &&
                    tmpEntry.getType().getPointLevel() > 0
            ) {
                res.add(new SemanticError("Pointer --" + ID + "-- cannot be dereferenced since it has still not been assigned"));
                tmpEntry.getType().setStatus(Status.ERROR);
                env.symTable.get(tmpEntry.getNestinglevel()).replace(this.ID, tmpEntry);
            }

        }

        if (this.pointLevel > 0 && tmpEntry.getType().getStatus() == Status.DELETED) {
            res.add(new SemanticError("Cannot dereference an already deleted pointer"));
            Node tmpLhs = tmpEntry.getType();
            tmpLhs.setStatus(Status.ERROR);
            STentry newEntry = new STentry(tmpEntry.getNestinglevel(), tmpLhs, tmpEntry.getOffset());
            env.symTable.get(tmpEntry.getNestinglevel()).replace(this.getID(), newEntry);

        }

        return res;
    }

    @Override
    public String codeGeneration(int nestingLevel) {
        String lookup = "";
        for (int i = nestingLevel; i > this.entry.getNestinglevel(); i--)
            lookup += "lw $al $al\n";
        return "lw $al $fp\n" +
                lookup +
                "addi $al " + this.entry.getOffset() + "\n" +
                "lw $a0 $al\n";
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

    public String getID() {
        return ID;
    }

    public void setRightHandSide() {
        isRightHandSide = true;
    }


}