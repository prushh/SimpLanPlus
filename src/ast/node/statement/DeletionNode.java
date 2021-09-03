package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Deletion statement node.
 *
 * deletion : 'delete' ID;
 */

public class DeletionNode implements Node {

    private String ID;
    private STentry entry;

    public DeletionNode(String ID) {
        this.ID = ID;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;

        while (idLevel >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(idLevel--)).get(ID);
        }

        if (tmpEntry == null) {
            res.add(new SemanticError("Id " + ID + " not declared"));
        } else {
            this.entry = tmpEntry;
        }
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (this.entry.getType().getPointLevel() == 0) {
            typeErr.add(new SemanticError("cannot delete a non pointer id"));
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;

        while (idLevel >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(idLevel--)).get(ID);
        }

        Status deletionStatus = Status.DELETED;
        deletionStatus = SimpLanPlusLib.seqStatus(tmpEntry.getType().getStatus(), deletionStatus);
        Node lhs = tmpEntry.getType();
        lhs.setStatus(deletionStatus);
        STentry newEntry = new STentry(tmpEntry.getNestinglevel(), lhs, tmpEntry.getOffset());
        env.symTable.get(tmpEntry.getNestinglevel()).replace(ID, newEntry);

        if (deletionStatus == Status.ERROR) {
            res.add(new SemanticError("Cannot delete an already deleted pointer"));
        }

        return res;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Deletion: " + ID + "\n";
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        String lookup = "";

        for (int i = env.getNestingLevel(); i > this.entry.getNestinglevel(); i--)
            lookup += "lw $al $al\n";

        String res = "";
        res += "cal\n" + lookup + "addi $al " + this.entry.getOffset() + "\n" + "si -10000 $al\n";

        return res;
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
