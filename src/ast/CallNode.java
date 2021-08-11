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
        ArrayList<Node> p = t.getParList();
        if (!(p.size() == args.size())) {
            System.out.println("Wrong number of parameters in the invocation of " + ID);
            System.exit(0);
        }
        if (isCallExp){
            if (SimpLanlib.isSubtype(t.getRet(),new VoidTypeNode())){
                System.out.println("cannot use void function as an exp");
                System.exit(0);
            }
        }
        for (int i = 0; i < args.size(); i++){
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
            this.nestingLevel = env.nestingLevel;

            for (Node arg : args)
                res.addAll(arg.checkSemantics(env));
        }

        return res;
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }


}