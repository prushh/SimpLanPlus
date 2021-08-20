package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;
import util.Status;

import java.util.ArrayList;

public class DeletionNode implements Node {

    private String ID;

    public DeletionNode(String ID) {
        this.ID = ID;
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
        return indent + "Deletion: " +
                ID + "\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        // need symbol table
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
            res.add(new SemanticError("Cannot delete an already deleted variable"));
        }

        // TODO considerare state dell'exp su nodo return

        return res;
    }

    @Override
    public String codeGeneration() {
        return null;
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
            if (env.symTable.get(tmpEntry.getNestinglevel()).get(ID).getType().getPointLevel() == 0) {
                res.add(new SemanticError("cannot delete a non pointer id"));
            }
        }
        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
