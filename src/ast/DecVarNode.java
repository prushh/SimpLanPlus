package ast;

import java.util.ArrayList;
import java.util.HashMap;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

public class DecVarNode implements Node {

    private String id;
    private Node type;
    private Node exp;

    public DecVarNode(String id, Node type, Node exp) {
        this.id = id;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        //env.offset = -2;
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, type, env.offset--); //separo introducendo "entry"

        if (hm.put(id, entry) != null)
            res.add(new SemanticError("Var id " + id + " already declared"));

        res.addAll(exp.checkSemantics(env));

        return res;
    }

    public String toPrint(String s) {
        return s + "Var:" + id + "\n"
                + type.toPrint(s + "  ")
                + exp.toPrint(s + "  ");
    }

    //valore di ritorno non utilizzato
    public Node typeCheck() {
        if (!(SimpLanlib.isSubtype(exp.typeCheck(), type))) {
            System.out.println("incompatible value for variable " + id);
            System.exit(0);
        }
        return null;
    }

    public String codeGeneration() {
        return exp.codeGeneration();
    }

}  