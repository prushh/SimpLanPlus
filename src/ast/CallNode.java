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
    public Node typeCheck(ArrayList<SemanticError> typeErr) {

        ArrowTypeNode t = null;
        if (entry.getType() instanceof ArrowTypeNode) t = (ArrowTypeNode) entry.getType();
        else {
            typeErr.add(new SemanticError("Invocation of a non-function " + ID));
        }
        ArrayList<Node> p = t.getArgList();
        if (!(p.size() == args.size())) {
            typeErr.add(new SemanticError("Wrong number of parameters in the invocation of " + ID));
        }
        if (isCallExp) {
            if (SimpLanlib.isSubtype(t.getRet(), new VoidTypeNode(Status.DECLARED))) {
                typeErr.add(new SemanticError("cannot use void function as an exp"));
            }
        }
        for (int i = 0; i < args.size(); i++) {
            Node arg_i = args.get(i).typeCheck(typeErr);
            if (!(SimpLanlib.isSubtype(arg_i, p.get(i))) || (arg_i.getPointLevel() != p.get(i).getPointLevel())) {
                typeErr.add(new SemanticError("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + ID));
            }
        }
        return t.getRet();
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
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