package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;

public class CallNode implements Node {

    private String ID;
    private STentry entry;
    private ArrayList<Node> args;
    private boolean isCallExp = false;

    public CallNode(String ID, ArrayList<Node> args) {
        this.ID = ID;
        this.args = args;
    }

    public void setCallExp() {
        isCallExp = true;
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
    public Node typeCheck() {  //

        ArrowTypeNode t = null;
        if (entry.getType() instanceof ArrowTypeNode) t = (ArrowTypeNode) entry.getType();
        else {
            System.out.println("Invocation of a non-function " + ID);
            System.exit(0);
        }
        ArrayList<Node> p = t.getArgList();
        if (!(p.size() == args.size())) {
            System.out.println("Wrong number of parameters in the invocation of " + ID);
            System.exit(0);
        }
        if (isCallExp) {
            if (SimpLanlib.isSubtype(t.getRet(), new VoidTypeNode(Status.DECLARED))) {
                System.out.println("cannot use void function as an exp");
                System.exit(0);
            }
        }
        for (int i = 0; i < args.size(); i++) {
            Node arg_i = args.get(i).typeCheck();
            if (!(SimpLanlib.isSubtype(arg_i, p.get(i))) || (arg_i.getPointLevel() != p.get(i).getPointLevel())) {
                System.out.println("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + ID);
                System.exit(0);
            }
        }
        return t.getRet();
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

            for (Node arg : args) {
                res.addAll(arg.checkSemantics(env));
            }
        }

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }
}