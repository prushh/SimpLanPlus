package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeletionNode implements Node {

    private String ID;
    private LhsNode lhs;

    public DeletionNode(String ID) {
        this.ID = ID;
        this.lhs = new LhsNode(ID,0);
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        return new NullTypeNode();
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
        }
        else {
            if (env.symTable.get(env.nestingLevel).get(ID).getType().getPointLevel() == 0){
                System.out.println("cannot delete a non pointer id");
                System.exit(0);
            }
        }
        return res;
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }

}
