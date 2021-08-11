package ast;

import java.util.ArrayList;
import java.util.HashMap;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

public class DecVarNode implements Node {

    private String ID;
    private Node type;
    private Node exp;

    public DecVarNode(String ID, Node type, Node exp) {
        this.ID = ID;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        Node l = this.type;
        if (exp != null) {
            Node r = this.exp.typeCheck();
            if (!(SimpLanlib.isSubtype(l, r))) {
                System.out.println("incompatible value for variable " + this.ID);
                System.exit(0);
            } else {
                if (l.getPointLevel() != r.getPointLevel()) {
                    System.out.println("cannot assign variable or pointers of different type");
                    System.exit(0);
                }
            }
        }
        return new NullTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, type, env.offset--);

        if (exp != null) {
            res.addAll(exp.checkSemantics(env));
        }

        if (hm.put(ID, entry) != null) {
            res.add(new SemanticError("DecVar " + ID + " already declared"));
        }

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}