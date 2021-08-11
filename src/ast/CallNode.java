package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

public class CallNode implements Node {

    private String ID;
    private STentry entry;
    private ArrayList<Node> args;
    private int nestingLevel;
    private int pointLevel;
    private boolean isCallExp = false;

    public CallNode(String ID, STentry entry, ArrayList<Node> args, int pointLevel) {
        this.ID = ID;
        this.entry = entry;
        this.args = args;
        this.pointLevel = pointLevel;
    }

    public CallNode(String ID, ArrayList<Node> args) {
        this.ID = ID;
        this.args = args;
    }

    public void setCallExp() {
        isCallExp = true;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {  //
        /*
        ArrowTypeNode t = null;
        if (entry.getType() instanceof ArrowTypeNode) t = (ArrowTypeNode) entry.getType();
        else {
            System.out.println("Invocation of a non-function " + id);
            System.exit(0);
        }
        ArrayList<Node> p = t.getParList();
        if (!(p.size() == parlist.size())) {
            System.out.println("Wrong number of parameters in the invocation of " + id);
            System.exit(0);
        }
        for (int i = 0; i < parlist.size(); i++)
            if (!(SimpLanlib.isSubtype((parlist.get(i)).typeCheck(), p.get(i)))) {
                System.out.println("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + id);
                System.exit(0);
            }
        return t.getRet();
        */
        return new NullTypeNode();
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
            res.add(new SemanticError("Fun " + ID + " not declared"));
        } else {
            this.entry = tmpEntry;
            this.nestingLevel = env.nestingLevel;

            for (Node arg : args) {
                res.addAll(arg.checkSemantics(env));
            }
        }

        return res;
    }
}