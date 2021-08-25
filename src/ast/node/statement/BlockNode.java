package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.*;

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
        StringBuilder builder = new StringBuilder();
        for (Node dec : decList)
            builder.append(dec.toPrint(indent + "  "));
        for (Node stm : stmList)
            builder.append(stm.toPrint(indent + "  "));
        return indent + "Block\n" + builder;
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
                if (!SimpLanPlusLib.isSubtype(ret, check))
                    err = true;
            }
        }
        if (err) {
            typeErr.add(new SemanticError("Mismatching return types"));
        }

        if (SimpLanPlusLib.isSubtype(check, new BoolTypeNode(0, Status.DECLARED))) {
            return new BoolTypeNode(0, Status.DECLARED);
        } else if (SimpLanPlusLib.isSubtype(check, new IntTypeNode(0, Status.DECLARED))) {
            return new IntTypeNode(0, Status.DECLARED);
        } else if (SimpLanPlusLib.isSubtype(check, new VoidTypeNode(Status.DECLARED))) {
            return new VoidTypeNode(Status.DECLARED);
        } else {
            return new NullTypeNode(Status.DECLARED);
        }
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!isBlockFunction) {
            HashMap<String, STentry> hm = new HashMap<>();
            env.symTable.add(hm);
            env.nestingLevel++;
        }

        for (Node dec : decList) {
            res.addAll(dec.checkEffects(env));
        }

        for (Node stm : stmList) {
            res.addAll(stm.checkEffects(env));
        }

        if (!isBlockFunction) {
            env.symTable.remove(env.nestingLevel);
            env.nestingLevel--;
        }

        return res;
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();

        Label blockLabel = new Label();
        // --todo-- check if function blocks differ from normal block
        if (!isBlockFunction) {
            env.incrementNestingLevel();
            env.setLabel(blockLabel.getLabel());
        }

        for (Node dec : decList) {
            builder.append(dec.codeGeneration(env));
        }

        builder.append("cfp\n");

        for (Node stm : stmList) {
            builder.append(stm.codeGeneration(env));
        }

        if (!isBlockFunction) {
            env.decrementNestingLevel();
            builder.append(blockLabel.getLabel());
        }

        return builder.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!isBlockFunction) {
            HashMap<String, STentry> hm = new HashMap<>();
            env.symTable.add(hm);
            env.nestingLevel++;
            env.offset = 0;
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
