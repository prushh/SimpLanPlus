package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockNode implements Node {

    private ArrayList<Node> decList;
    private ArrayList<Node> stmList;
    private boolean isBlockFunction = false;

    public BlockNode(ArrayList<Node> decList, ArrayList<Node> stmList) {
        this.decList = decList;
        this.stmList = stmList;
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

        for (Node dec : decList)
            dec.typeCheck(typeErr);

        Node tmp;
        ArrayList<Node> nodeList = new ArrayList<>();
        for (Node stm : stmList) {
            tmp = stm.typeCheck(typeErr);
            if ((stm instanceof RetNode) || (stm instanceof IteNode && !(tmp instanceof NullTypeNode)))
                nodeList.add(tmp);
        }
        Node check;
        boolean err = false;
        if (nodeList.isEmpty()) {
            return new NullTypeNode(Status.DECLARED);
        } else {
            check = nodeList.get(0);
            for (Node ret : nodeList) {
                if (!SimpLanlib.isSubtype(ret, check))
                    err = true;
            }
        }
        if (err) {
            typeErr.add(new SemanticError("Mismatching return types"));
        }

        if (SimpLanlib.isSubtype(check, new BoolTypeNode(0, Status.DECLARED))) {
            return new BoolTypeNode(0, Status.DECLARED);
        } else if (SimpLanlib.isSubtype(check, new IntTypeNode(0, Status.DECLARED))) {
            return new IntTypeNode(0, Status.DECLARED);
        } else if (SimpLanlib.isSubtype(check, new VoidTypeNode(Status.DECLARED))) {
            return new VoidTypeNode(Status.DECLARED);
        } else {
            return new NullTypeNode(Status.DECLARED);
        }
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

        if (!isBlockFunction) {
            HashMap<String, STentry> hm = new HashMap<>();
            env.symTable.add(hm);
            env.nestingLevel++;
        }

        for (Node dec : decList) {
            res.addAll(dec.checkSemantics(env));
        }

        for (Node stm : stmList) {
            res.addAll(stm.checkSemantics(env));
        }

        if (!isBlockFunction) {
            env.symTable.remove(env.nestingLevel);
            env.nestingLevel--;
        }

        return res;
    }

    public void setBlockFunction() {
        isBlockFunction = true;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
