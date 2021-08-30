package util;

import ast.Node;
import ast.STentry;
import ast.node.other.ArgNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.VoidTypeNode;
import org.antlr.v4.runtime.Token;
import parser.SVMParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpLanPlusLib {

    //valuta se il tipo "a" <= al tipo "b", dove "a" e "b" sono tipi di base: int o bool
    public static boolean isSubtype(Node a, Node b) {
        return a.getClass().equals(b.getClass()); //||
        // ( (a instanceof BoolTypeNode) && (b instanceof IntTypeNode) ); //
    }

    public static Status maxStatus(Status s1, Status s2) {
        if (s1.ordinal() <= s2.ordinal())
            return s2;
        else
            return s1;
    }

    public static Status seqStatus(Status s1, Status s2) {
        Status tmp = maxStatus(s1, s2);
        if (tmp.ordinal() <= Status.READWRITE.ordinal())
            return (tmp);
        else if ((s1.ordinal() <= Status.READWRITE.ordinal() && s2 == Status.DELETED)
                || (s1 == Status.DELETED && s2 == Status.READWRITE)
        )
            return Status.DELETED;
        else
            return Status.ERROR;
    }

    public static Status parStatus(Status s1, Status s2) {
        return maxStatus(seqStatus(s1, s2), seqStatus(s2, s1));
    }

    public static Environment cloneEnvironment(Environment env) {
        Environment newEnv = new Environment(env.nestingLevel, env.offset);
        for (HashMap<String, STentry> map : env.symTable){
            newEnv.symTable.add(new HashMap<>());
            for (Map.Entry<String, STentry> entry : map.entrySet()) {
                HashMap<String, STentry> newMap =  newEnv.symTable.get(newEnv.symTable.size()-1);
                int newNestingLevel = entry.getValue().getNestinglevel();
                Node newType = entry.getValue().getType();
                int newPointLevel = newType.getPointLevel();
                Status newStatus = newType.getStatus();
                int newOffset = entry.getValue().getOffset();
                Node newEnvType;
                if (newType instanceof ArgNode){
                    System.out.println("ERRORE FATALE");
                    System.out.println("GAGAGAGAGAGAGA");
                    System.exit(0);
                }
                if (newType instanceof BoolTypeNode){
                    newEnvType = new BoolTypeNode(newPointLevel,newStatus);
                }
                else if (newType instanceof IntTypeNode){
                    newEnvType = new IntTypeNode(newPointLevel,newStatus);
                }
                else {
                    ArrayList<ArgNode> newArgList = new ArrayList<>();
                    ArrowTypeNode tmpArrowType =  ((ArrowTypeNode) newType);
                    for (ArgNode arg: tmpArrowType.getArgList()) {
                        ArgNode tmpArgNode = arg;
                        String argId = tmpArgNode.getId();
                        Node newArgType = tmpArgNode.getType();
                        int newArgPoint = newArgType.getPointLevel();
                        Status newArgStatus = newArgType.getStatus();
                        Node newEnvArgType;
                        if (newArgType instanceof BoolTypeNode){
                            newEnvArgType = new BoolTypeNode(newArgPoint,newArgStatus);
                        }
                        else {
                            newEnvArgType = new IntTypeNode(newArgPoint,newArgStatus);
                        }
                        newArgList.add(new ArgNode(argId, newEnvArgType));
                    }
                    Node newArrowRetType = ((ArrowTypeNode) newType).getRet();
                    Node newEnvArrowRetType;

                    if (newArrowRetType instanceof BoolTypeNode) {
                        newEnvArrowRetType = new BoolTypeNode(0, Status.DECLARED);
                    } else if (newArrowRetType instanceof IntTypeNode) {
                        newEnvArrowRetType = new IntTypeNode(0, Status.DECLARED);
                    } else {
                        newEnvArrowRetType = new VoidTypeNode(Status.DECLARED);
                    }
                    newEnvType = new ArrowTypeNode(newArgList, newEnvArrowRetType);
                }
                newMap.put(entry.getKey(), new STentry(newNestingLevel, newEnvType, newOffset));
            }
        }

        return newEnv;
    }

    public static int getRegister(Token register) {
        switch (register.getText()) {
            case "$a0":
                return SVMParser.A0;
            case "$t0":
                return SVMParser.T0;
            case "$sp":
                return SVMParser.SP;
            case "$ra":
                return SVMParser.RA;
            case "$fp":
                return SVMParser.FP;
            case "$al":
                return SVMParser.AL;
            case "$hp":
                return SVMParser.HP;
            case "$rv":
                return SVMParser.RV;
            default:
                return -1;
        }
    }


}