package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockNode implements Node {

    private ArrayList<Node> decList;
    private ArrayList<Node> stmList;

    public BlockNode(ArrayList<Node> decList, ArrayList<Node> stmList) {
        this.decList = decList;
        this.stmList = stmList;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {

        for (Node dec : decList)
            dec.typeCheck();

        Node tmp;
        ArrayList<Node> nodeList = new ArrayList<>();
        for (Node stm : stmList) {
            tmp = stm.typeCheck();
            if ((stm instanceof RetNode) || (stm instanceof IteNode  &&  ! (tmp instanceof NullTypeNode) ))
                nodeList.add(tmp);
        }
        Node check;
        boolean err = false;
        if (nodeList.isEmpty()) {
            return new NullTypeNode();
        } else {
            check = nodeList.get(0);
            for (Node ret : nodeList) {
                if (!SimpLanlib.isSubtype(ret,check))
                    err = true;
            }
        }
        if (err) {
            System.out.println("Mismatching return types");
            System.exit(0);
        }

        if (SimpLanlib.isSubtype(check, new BoolTypeNode(0))) {
            return new BoolTypeNode(0);
        }
        else if (SimpLanlib.isSubtype(check, new IntTypeNode(0))) {
            return new IntTypeNode(0);
        }
        else if (SimpLanlib.isSubtype(check, new VoidTypeNode())) {
            return new VoidTypeNode();
        }
        else {
            return new NullTypeNode();
        }
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        HashMap<String, STentry> hm = new HashMap<>();
        env.symTable.add(hm);
        env.nestingLevel += 1;

        for (Node dec : decList) {
            res.addAll(dec.checkSemantics(env));
        }

        for (Node stm : stmList) {
            res.addAll(stm.checkSemantics(env));
        }

        return res;
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }

}
