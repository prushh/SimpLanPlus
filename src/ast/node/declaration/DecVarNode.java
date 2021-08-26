package ast.node.declaration;

import ast.Node;
import ast.STentry;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DecVarNode implements Node {

    private String ID;
    private Node type;
    private Node exp;

    public DecVarNode(String ID, Node type, Node exp) {
        this.ID = ID;
        this.type = type;
        this.exp = exp;
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
        String str = indent + "DecVar: " +
                ID + "\n" +
                type.toPrint(indent + "\t");
        builder.append(str);
        if (exp != null) {
            builder.append(exp.toPrint(indent + "\t"));
        }
        return builder.toString();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node l = this.type;
        if (exp != null) {
            Node r = this.exp.typeCheck(typeErr);
            if (!(SimpLanPlusLib.isSubtype(l, r))) {
                typeErr.add(new SemanticError("incompatible value for variable " + this.ID));
            } else {
                if (l.getPointLevel() != r.getPointLevel()) {
                    typeErr.add(new SemanticError("cannot assign variable or pointers of different type"));
                }
            }
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry;

        if (exp != null) {
            res.addAll(exp.checkEffects(env));
            Status newDecAsg = Status.READWRITE;
            Node tmpLhs = type;
            tmpLhs.setStatus(newDecAsg);
            entry = new STentry(env.nestingLevel, tmpLhs, env.offset--);
        } else {
            entry = new STentry(env.nestingLevel, type, env.offset--);
        }
        hm.put(ID, entry);

        return res;
    }


    @Override
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        if (exp != null) {
            builder.append(exp.codeGeneration(env));
            builder.append("push $a0\n");
        } else {
            builder.append("addi $sp -1\n");
        }

        return builder.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, type, env.offset--);

        if (exp != null) {
            res.addAll(exp.checkSemantics(env));
        }

        if (hm.put(ID, entry) != null) {
            res.add(new SemanticError("DecVar " + ID + " already declared"));
        }

        return res;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

}