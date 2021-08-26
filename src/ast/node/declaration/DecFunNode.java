package ast.node.declaration;

import ast.Node;
import ast.STentry;
import ast.node.other.ArgNode;
import ast.node.statement.BlockNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DecFunNode implements Node {

    private String ID;
    private Node type;
    private ArrayList<ArgNode> args;
    private Node body;

    public DecFunNode(String ID, Node type, Node body) {
        this.ID = ID;
        this.type = type;
        this.body = body;
        args = new ArrayList<>();
    }

    public void addArg(ArgNode arg) {
        args.add(arg);
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
        String str = indent + "DecFun: " +
                ID + "\n" +
                type.toPrint(indent + "\t");
        builder.append(str);
        for (ArgNode arg : args) {
            builder.append(arg.toPrint(indent + "\t"));
        }
        builder.append(body.toPrint(indent + "\t"));
        return builder.toString();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node bodyTmp = body.typeCheck(typeErr);
        if (this.type.getPointLevel() == 0) {
            if (!SimpLanPlusLib.isSubtype(this.type, bodyTmp)) {
                typeErr.add(new SemanticError("Mismatching return types <function = " + this.type + ", body = " + bodyTmp + ">"));
            }
        } else {
            typeErr.add(new SemanticError("Function can not have pointer type"));
        }
        return bodyTmp;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        Environment effectEnv = new Environment();
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, env.offset--);

        hm.put(ID, entry);

        effectEnv.nestingLevel++;
        HashMap<String, STentry> hmFun = new HashMap<>();
        effectEnv.symTable.add(effectEnv.nestingLevel, hmFun);

        int getFunEnv = env.nestingLevel;
        while (getFunEnv >= 0) {
            for (Map.Entry<String, STentry> funEntry : env.symTable.get(getFunEnv).entrySet()) {
                if (funEntry.getValue().getType() instanceof ArrowTypeNode) {
                    hmFun.put(funEntry.getKey(), funEntry.getValue());
                }
            }
            getFunEnv--;
        }

        entry.addType(new ArrowTypeNode(args, this.type));

        hmFun.put(ID, entry);

        //// ----- effectEnv == SigmaFUN

        effectEnv.nestingLevel++;
        HashMap<String, STentry> hmn = new HashMap<>();

        effectEnv.symTable.add(hmn);

        int argOffset = 1;

        for (ArgNode arg : args) {
            if (arg.getType() instanceof IntTypeNode) {
                hmn.put(arg.getId(), new STentry(effectEnv.nestingLevel, new IntTypeNode(arg.getType().getPointLevel(), arg.getType().getStatus()), argOffset++));
            } else {
                hmn.put(arg.getId(), new STentry(effectEnv.nestingLevel, new BoolTypeNode(arg.getType().getPointLevel(), arg.getType().getStatus()), argOffset++));
            }

        }


        /* -- todo gestire offset per variabili nella funzione
        if (args.size() > 0) {
            env.offset = -2;

            for (Node n : args) {
                res.addAll(n.checkSemantics(env));
            }
        }
        */


        ArrayList<ArgNode> sigma0;
        ArrayList<ArgNode> sigma1 = args;
        ArrayList<SemanticError> fixPointErrors;
        boolean checkFixPoint = false;

        for (ArgNode arg : args) {
            effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().setStatus(Status.READWRITE);
        }

        do {

            fixPointErrors = new ArrayList<>();

            checkFixPoint = false;

            sigma0 = new ArrayList<>();


            for (int i = 0; i < sigma1.size(); i++) {
                if (sigma1.get(i).getType() instanceof IntTypeNode)
                    sigma0.add(new ArgNode(sigma1.get(i).getId(), new IntTypeNode(sigma1.get(i).getPointLevel(), sigma1.get(i).getType().getStatus())));
                else if (sigma1.get(i).getType() instanceof BoolTypeNode)
                    sigma0.add(new ArgNode(sigma1.get(i).getId(), new BoolTypeNode(sigma1.get(i).getPointLevel(), sigma1.get(i).getType().getStatus())));
            }

            //Environment effectEnv = SimpLanPlusLib.cloneEnvironment(env);

            fixPointErrors.addAll(body.checkEffects(effectEnv));

            // Swap local variables status with args if local vars are formal parameters
            for (ArgNode arg : args) {
                Status localVar = effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().getStatus();
                //Status arrowArgStatus = arg.getType().getStatus();
                effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().setStatus(Status.READWRITE);
                ArrayList<ArgNode> tmpArgList = ((ArrowTypeNode) effectEnv.symTable.get(effectEnv.nestingLevel - 1).get(this.ID).getType()).getArgList();
                for (ArgNode arrowArg : tmpArgList) {
                    if (effectEnv.symTable.get(effectEnv.nestingLevel).containsKey(arrowArg.getId()) &&
                            arrowArg.getId() == arg.getId()) {
                        arrowArg.getType().setStatus(localVar);
                    }
                }
            }

            // argEffectList = /sigma1
            sigma1 = new ArrayList<>();

            for (ArgNode a : ((ArrowTypeNode) effectEnv.symTable.get(effectEnv.nestingLevel - 1).get(this.ID).getType()).getArgList()) {
                ArgNode tmpArg = new ArgNode(a.getId(), a.getType());
                sigma1.add(tmpArg);
            }

            // System.out.println("Size sigma0: " + sigma0.size());
            // System.out.println("Size sigma1: " + sigma1.size());
            for (int idx = 0; idx < sigma0.size(); idx++) {
                if (sigma0.get(idx).getType().getStatus() != sigma1.get(idx).getType().getStatus()) {
                    checkFixPoint = true;
                    break;
                }
            }

        }
        while (checkFixPoint);

        //res.addAll(body.checkEffects(effectEnv));

        // -- todo fine punto fisso


        entry.addType(new ArrowTypeNode(sigma1, this.type));

        res.addAll(fixPointErrors);
        effectEnv.symTable.remove(effectEnv.nestingLevel);
        effectEnv.nestingLevel--;
        return res;
    }


    @Override
    public String codeGeneration(CGenEnv env) {
        Label label = new Label();
        StringBuilder popArgs = new StringBuilder();
        for (int i = 0; i < args.size(); i++)
            popArgs.append("pop\n");
        popArgs.append("pop\n");

        StringBuilder popLocal = new StringBuilder();
        ArrayList<Node> decList = ((BlockNode) body).getDecList();
        for (int i = 0; i < decList.size(); i++)
            popLocal.append("pop\n");

        StringBuilder builder = new StringBuilder();
        builder.append("b ")
                .append(label.getLabel())
                .append("\n")
                .append("__")
                .append(this.ID)
                .append("_:\n")
                .append("cal\n")
                .append("cfp\n")
                .append("push $al\n")
                .append("cfp\n")
                .append("push $ra\n")
                .append(this.body.codeGeneration(env))
                .append(popLocal)
                .append("lw $a0 $sp\n")
                .append("sra\n")
                .append(popArgs)
                .append("lw $a0 $sp\n")
                .append("sfp\n")
                .append("pop\n")
                .append("jr $ra\n")
                .append(label.getLabel())
                .append(":\n");

        return builder.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, env.offset--);

        if (hm.put(ID, entry) != null) {
            res.add(new SemanticError("Fun id " + ID + " already declared"));
        } else {
            Environment funEnv = new Environment();
            funEnv.nestingLevel++;
            funEnv.offset++; // because at $fp there's the access link
            HashMap<String, STentry> hmFun = new HashMap<>();

            if (hmFun.put(ID, entry) != null) {
                res.add(new SemanticError("Fun id " + ID + " already declared"));
            }

            int getFunEnv = env.nestingLevel;
            while (getFunEnv >= 0) {
                for (Map.Entry<String, STentry> funEntry : env.symTable.get(getFunEnv).entrySet()) {
                    if (funEntry.getValue().getType() instanceof ArrowTypeNode) {
                        hmFun.put(funEntry.getKey(), funEntry.getValue());
                    }
                }
                getFunEnv--;
            }

            entry.addType(new ArrowTypeNode(args, this.type));

            hmFun.put(ID, entry);

            funEnv.symTable.add(hmFun);
            //env.symTable.add(hmn);

            funEnv.nestingLevel++;
            HashMap<String, STentry> hmArg = new HashMap<>();

            funEnv.symTable.add(hmArg);

            for (ArgNode arg : args) {
                if (hmArg.put(arg.getId(), new STentry(funEnv.nestingLevel, arg.getType(), funEnv.offset++)) != null) {
                    res.add(new SemanticError("Parameter id " + arg.getId() + " already declared"));
                }
            }

            entry.addType(new ArrowTypeNode(args, type));

            /*
            --todo--
            if (args.size() > 0) {
                env.offset = -2;
            }
            */
            funEnv.offset = -2;

            res.addAll(body.checkSemantics(funEnv));
            funEnv.symTable.remove(funEnv.nestingLevel);
            funEnv.nestingLevel--;
        }

        return res;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}