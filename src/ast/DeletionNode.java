package ast;

import util.Environment;
import util.SemanticError;
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
        return null;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        // need symbol table
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.symTable.get(env.nestingLevel).containsKey(ID)) {
            res.add(new SemanticError("Id " + ID + " not declared"));
        } else {
            if (env.symTable.get(env.nestingLevel).get(ID).getType().getPointLevel() == 0) {
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
