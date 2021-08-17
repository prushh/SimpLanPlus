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

        ArrowTypeNode functionType = null;
        if (entry.getType() instanceof ArrowTypeNode) functionType = (ArrowTypeNode) entry.getType();
        else {
            typeErr.add(new SemanticError("Invocation of a non-function " + ID));
        }
        ArrayList<ArgNode> argList = functionType.getArgList();
        if (!(argList.size() == args.size())) {
            typeErr.add(new SemanticError("Wrong number of parameters in the invocation of " + ID));
        }
        if (isCallExp) {
            if (SimpLanlib.isSubtype(functionType.getRet(), new VoidTypeNode(Status.DECLARED))) {
                typeErr.add(new SemanticError("cannot use void function as an exp"));
            }
        }
        for (int i = 0; i < args.size(); i++) {
            Node arg_i = args.get(i).typeCheck(typeErr);
            if (!(SimpLanlib.isSubtype(arg_i, argList.get(i).getType())) || (arg_i.getPointLevel() != argList.get(i).getType().getPointLevel())) {
                typeErr.add(new SemanticError("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + ID));
            }
        }
        return functionType.getRet();
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int i = env.nestingLevel;
        STentry tmpEntry = null;

        while (i >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(i--)).get(ID);
        }

        for (Node arg : args) {
            res.addAll(arg.checkEffects(env));
        }

        // Puntatori possibili ---> lhs (pointLevel > 0) e baseExp che contiene un puntatore
        // Tutto il resto viene passato per valore

        ArrayList<DerExpNode> pointerList = new ArrayList<>();
        for (Node arg : args) {
            Node tmp = arg;
            if (arg instanceof BaseExpNode){
                while (((BaseExpNode)tmp).getExp() instanceof BaseExpNode){
                    tmp = ((BaseExpNode)tmp).getExp();
                }
           }
           if (tmp instanceof DerExpNode){
               DerExpNode tmpDerNode = (DerExpNode) tmp;
               int tmpNest = env.nestingLevel;
               while (env.symTable.get(tmpNest).get(tmpDerNode.getLhsNode().getID()) == null){
                   tmpNest --;
               }
               if (env.symTable.get(tmpNest).get(tmpDerNode.getLhsNode().getID()).getType().getPointLevel() -
                    tmpDerNode.getLhsNode().getPointLevel()>0)
                   pointerList.add(tmpDerNode);
           }
        }

        for (DerExpNode arg : pointerList) {
            int tmpNest = env.nestingLevel;
            while (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()) == null){
                tmpNest --;
            }
            if (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()).getType().getStatus().ordinal() >= Status.DELETED.ordinal()){
                res.add(new SemanticError("Cannot pass a deleted pointer as an actual parameter"));
            }
        }

        return res;
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