package ast.node.other;

import ast.Node;
import ast.STentry;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Left-hand-side node.
 *
 * lhs : ID | lhs '^' ;
 */

public class LhsNode implements Node {

    private String ID;
    private STentry entry;
    private int pointLevel;
    private boolean isRightHandSide = false;

    public LhsNode(String ID, int pointLevel) {
        this.ID = ID;
        this.pointLevel = pointLevel;
    }

    public String getID() {
        return ID;
    }

    public void setRightHandSide() {
        isRightHandSide = true;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int i = env.nestingLevel;
        STentry tmpEntry = null;

        // Look up identifier in the symbol table
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
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        // Can't use a function as left-hand-side
        if (entry.getType() instanceof ArrowTypeNode) {
            typeErr.add(new SemanticError("wrong usage of function identifier"));
        }

        /*
         * Check on pointers:
         * -entry.getType().getPointLevel() is the point level reported in symbol
         * table
         * -this.point level is the number of '^' used on the right of the identifier
         */
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

        // Get symbol table entry for ID
        while (idLevel >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(idLevel--)).get(ID);
        }

        /*
         * Sure after checkSemantics and typeCheck, pointers are accessed
         * and assigned with the right number of '^'. The problem now is to
         * detect errors like deleted pointers used as lhs or rhs
         */
        if (isRightHandSide) {

            int difference = entry.getType().getPointLevel() - this.pointLevel;

            if (difference == 0) {

                // Here we are dealing with normal variables, simple int or bool
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

                /*
                 *  Here we are dealing with pointers. We assume that a pointer has status
                 *  READWRITE only after initialization/assignment with a new expression.
                 */
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
            // Now we consider left-hand-side nodes
            if (tmpEntry.getType().getStatus() != Status.READWRITE && (this.pointLevel > 0)
                    && tmpEntry.getType().getPointLevel() > 0) {
                if (tmpEntry.getType().getStatus() == Status.DECLARED)
                    res.add(new SemanticError(
                            "Pointer --" + ID + "-- cannot be dereferenced since it has still not been assigned"));
                else
                    res.add(new SemanticError(
                            "Pointer --" + ID + "-- cannot be dereferenced since it has been deleted"));
                tmpEntry.getType().setStatus(Status.ERROR);
                env.symTable.get(tmpEntry.getNestinglevel()).replace(this.ID, tmpEntry);
            }
        }

        return res;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pointLevel; i++) {
            builder.append("^");
        }
        String str = "\n" + indent + "at nesting level " + this.entry.getNestinglevel() + " with offset "
                + this.entry.getOffset();
        builder.append(str);
        return indent + this.ID + builder + "\n";
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        String lookup = "";
        /*
         * Thanks to the CGenEnv we know the current nesting level and we can
         * get the number of jumps through the access link to get lhs value
         */
        for (int i = env.getNestingLevel(); i > this.entry.getNestinglevel(); i--)
            lookup += "lw $al $al\n";
        String res = "";
        /*
         * Frame pointer is copied to access link register because $fp points to a
         * location in memory that contains access link. Then the lw $al $al allows
         * to go up through the static chain
         */
        res += "cal\n" + lookup + "addi $al " + this.entry.getOffset() + "\n";

        /*
         *  Once we found the memory address, if we have a pointer, then we must also
         *  retrieve value from heap
         */
        for (int i = 0; i < this.pointLevel; i++) {
            res += "lw $al $al\n";
        }

        // Now $al has the address of the value we are interested
        if (isRightHandSide) {
            /*
             * If the node is a right-hand-side we need the value stored at address
             * pointed by $al
             */
            res += "lw $a0 $al\n";
        } else {
            /* If the node is a left-hand-side we need the address already store
             * in $al
             */
            res += "lal\n";
        }
        return res;
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {

    }

}