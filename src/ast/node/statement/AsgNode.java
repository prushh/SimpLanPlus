package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.exp.NewExpNode;
import ast.node.other.LhsNode;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.Objects;

public class AsgNode implements Node {

    private LhsNode lhs;
    private Node exp;

    public AsgNode(LhsNode lhs, Node exp) {
        this.lhs = lhs;
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
        return indent + "Assignment\n" +
                lhs.toPrint(indent + "\t") +
                exp.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node lhs = this.lhs.typeCheck(typeErr);
        Node exp = this.exp.typeCheck(typeErr);
        if (!(SimpLanPlusLib.isSubtype(lhs, exp))) {
            typeErr.add(new SemanticError("incompatible value for variable " + this.lhs.getID()));
        } else {
            if (!Objects.equals(lhs.getPointLevel(), exp.getPointLevel())) {
                typeErr.add(new SemanticError("cannot assign variable or pointers of different type"));
            }
        }
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(lhs.checkEffects(env));

        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;

        while (idLevel >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(idLevel--)).get(lhs.getID());
        }

        if (exp != null) {
            if (SimpLanPlusLib.isSubtype(exp, new NewExpNode(new NullTypeNode(Status.DECLARED)))) {
                //  Case lhs.status == declared, bisognerebbe fare una seq tra declared e readwrite, quindi otteniamo readwrite
                //  Case lhs.status == readwrite, non succede niente
                //  Case lhs.status == deleted, riassegno una cella di memoria a lhs, torno a readwrite
                tmpEntry.getType().setStatus(Status.READWRITE);
            } else {
                res.addAll(exp.checkEffects(env));
                Status newlhsAsg = Status.READWRITE;
                newlhsAsg = SimpLanPlusLib.seqStatus(tmpEntry.getType().getStatus(), newlhsAsg);
                tmpEntry.getType().setStatus(newlhsAsg);
            }
            env.symTable.get(tmpEntry.getNestinglevel()).replace(lhs.getID(), tmpEntry);
        }

        return res;
    }

    @Override
    public String codeGeneration(CGenEnv env) {

        return exp.codeGeneration(env) +
                "push $a0\n" +
                lhs.codeGeneration(env) +
                "lw $t0 $sp\n" +
                "pop\n" +
                "sw $t0 $a0\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        res.addAll(lhs.checkSemantics(env));
        if (exp != null) {
            res.addAll(exp.checkSemantics(env));
        }

        return res;
    }


    @Override
    public int getPointLevel() {
        return 0;
    }

}
