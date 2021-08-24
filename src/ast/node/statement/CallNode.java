package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.exp.BaseExpNode;
import ast.node.exp.DerExpNode;
import ast.node.other.ArgNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;
import util.Status;

import java.util.ArrayList;
import java.util.HashMap;

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
        StringBuilder builder = new StringBuilder();
        String str = indent + "Call: " +
                ID + "\n";
        builder.append(str);
        String entryStr = indent + "at nesting level " + this.entry.getNestinglevel() +
                " with offset " + this.entry.getOffset() + "\n";
        builder.append(entryStr);

        for (Node exp : args) {
            builder.append(exp.toPrint(indent + "\t"));
        }
        return builder.toString();
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
        } else {

            for (int i = 0; i < args.size(); i++) {
                Node arg_i = args.get(i).typeCheck(typeErr);
                if (!(SimpLanPlusLib.isSubtype(arg_i, argList.get(i).getType())) || (arg_i.getPointLevel() != argList.get(i).getType().getPointLevel())) {
                    typeErr.add(new SemanticError("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + ID));
                }
            }
            if (isCallExp) {
                if (SimpLanPlusLib.isSubtype(functionType.getRet(), new VoidTypeNode(Status.DECLARED))) {
                    typeErr.add(new SemanticError("cannot use void function as an exp"));
                }
            } else {
                return new NullTypeNode(Status.DECLARED);
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
            if (arg instanceof BaseExpNode) {
                while (((BaseExpNode) tmp).getExp() instanceof BaseExpNode) {
                    tmp = ((BaseExpNode) tmp).getExp();
                }
            }
            if (tmp instanceof DerExpNode) {
                DerExpNode tmpDerNode = (DerExpNode) tmp;
                int tmpNest = env.nestingLevel;
                while (env.symTable.get(tmpNest).get(tmpDerNode.getLhsNode().getID()) == null) {
                    tmpNest--;
                }
                if (env.symTable.get(tmpNest).get(tmpDerNode.getLhsNode().getID()).getType().getPointLevel() -
                        tmpDerNode.getLhsNode().getPointLevel() > 0)
                    pointerList.add(tmpDerNode);
            }

        }

        for (DerExpNode arg : pointerList) {
            int tmpNest = env.nestingLevel;
            while (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()) == null) {
                tmpNest--;
            }
            if (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()).getType().getStatus() == Status.DELETED) {
                res.add(new SemanticError("Cannot pass a deleted pointer as an actual parameter"));
            } else if (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()).getType().getStatus() == Status.ERROR) {
                res.add(new SemanticError("Error in accessing id --" + arg.getLhsNode().getID() + "--"));
            }
        }

        for (DerExpNode arg : pointerList) {
            int tmpNest = env.nestingLevel;
            while (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()) == null) {
                tmpNest--;
            }
        }


        int tmpArrow = env.nestingLevel;
        while (env.symTable.get(tmpArrow).get(this.ID) == null) {
            tmpArrow--;
        }


        ArrowTypeNode arrowType = (ArrowTypeNode) env.symTable.get(tmpArrow).get(this.ID).getType();
        ArrayList<ArgNode> argsArrow = arrowType.getArgList();

        HashMap<String, ArrayList<Status>> aliasHsm = new HashMap<>();

        for (int idx = 0; idx < args.size(); idx++) {
            if (pointerList.contains(args.get(idx))) {
                DerExpNode arg = (DerExpNode) args.get(idx);
                ArgNode argArrow = argsArrow.get(idx);

                String idLhs = arg.getLhsNode().getID();
                int tmpNest = env.nestingLevel;
                while (env.symTable.get(tmpNest).get(idLhs) == null) {
                    tmpNest--;
                }

                //Status argStatus = env.symTable.get(tmpNest).get(idLhs).getType().getStatus();
                Status argStatusStart = Status.READWRITE;
                Status argStatus = env.symTable.get(tmpNest).get(idLhs).getType().getStatus();
                Status argArrowStatus = argArrow.getType().getStatus();

                Status seqFinal = SimpLanPlusLib.seqStatus(argStatusStart, argArrowStatus);

                if (aliasHsm.containsKey(idLhs)) {
                    aliasHsm.get(idLhs).add(seqFinal);
                } else {
                    ArrayList<Status> tmp = new ArrayList<>();
                    tmp.add(seqFinal);
                    aliasHsm.put(idLhs, tmp);
                }

                // Update[/sigma', /sigma'']
                env.symTable.get(tmpNest).get(idLhs).getType().setStatus(SimpLanPlusLib.maxStatus(seqFinal, argStatus));
            }
        }

        for (String key : aliasHsm.keySet()) {
            ArrayList<Status> values = aliasHsm.get(key);
            int size = values.size();
            Status finalStatus = Status.DECLARED;
            if (size > 1) {
                Status maxLocal = Status.DECLARED;
                for (int idx = 0; idx < size; idx++) {
                    for (int jdx = idx; jdx < size; jdx++) {
                        if (idx != jdx) {
                            Status prev = values.get(idx);
                            Status foll = values.get(jdx);
                            maxLocal = SimpLanPlusLib.parStatus(prev, foll);

                            //values.remove(jdx);
                            //values.add(maxLocal);
                        } else {
                            maxLocal = values.get(idx);
                        }
                    }
                    finalStatus = SimpLanPlusLib.maxStatus(maxLocal, finalStatus);
                }
                values.clear();
                values.add(finalStatus);
            } else {
                aliasHsm.get(key).clear();
            }
        }


        for (String key : aliasHsm.keySet()) {
            if (aliasHsm.get(key).size() != 0) {
                int level = env.nestingLevel;
                STentry entry = null;
                while (level >= 0 && entry == null) {
                    entry = env.symTable.get(level--).get(key);
                }
                Status max = aliasHsm.get(key).get(0);
                entry.getType().setStatus(max);
            }
        }

        for (String key : aliasHsm.keySet()) {
            if (aliasHsm.get(key).size() != 0) {
                if (aliasHsm.get(key).get(0) == Status.ERROR) {
                    res.add(new SemanticError("Aliasing error in function call " + this.ID + " on pointer: " + key));
                }
            }
        }

        return res;
    }

    @Override
    public String codeGeneration(int nestingLevel) {
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