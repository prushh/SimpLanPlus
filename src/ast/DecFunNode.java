package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.Status;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.copy;

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
        return null;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node bodyTmp = body.typeCheck(typeErr);
        if (this.type.getPointLevel() == 0) {
            if (!SimpLanlib.isSubtype(this.type, bodyTmp)) {
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

        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, env.offset--);

        hm.put(ID, entry);

        env.nestingLevel++;
        HashMap<String, STentry> hmn = new HashMap<>();

        hmn.put(ID, entry);

        env.symTable.add(hmn);

        int argOffset = 1;

        for (ArgNode arg : args) {
            hmn.put(arg.getId(), new STentry(env.nestingLevel, arg.getType(), argOffset++));
        }

        entry.addType(new ArrowTypeNode(args, this.type));

        /* -- todo gestire offset per variabili nella funzione
        if (args.size() > 0) {
            env.offset = -2;

            for (Node n : args) {
                res.addAll(n.checkSemantics(env));
            }
        }
        */

        ArrayList<ArgNode> argDeclared = new ArrayList<>();
        for (Node a : args) {
            argDeclared.add((ArgNode) a);
        }

        ArrayList<ArgNode> sigma0 = new ArrayList<>();
        for (int i = 0; i < argDeclared.size(); i++) {
            if (argDeclared.get(i).getType() instanceof IntTypeNode)
                sigma0.add(new ArgNode(argDeclared.get(i).getId(), new IntTypeNode(argDeclared.get(i).getPointLevel(), Status.DECLARED)));
            else if (argDeclared.get(i).getType() instanceof BoolTypeNode)
                sigma0.add(new ArgNode(argDeclared.get(i).getId(), new BoolTypeNode(argDeclared.get(i).getPointLevel(), Status.DECLARED)));
        }

        // -- todo punto fisso

        Environment effectEnv = SimpLanlib.cloneEnvironment(env);

        body.checkEffects(effectEnv);

        // argEffectList = /sigma1
        ArrayList<ArgNode> argEffectList = new ArrayList<>();

        for (ArgNode a : args) {
            Node tmp = effectEnv.symTable.get(effectEnv.nestingLevel).get(a.getId()).getType();
            ArgNode tmpArg = new ArgNode(a.getId(),tmp);
            argEffectList.add(tmpArg);
        }


        // -- todo fine punto fisso

        entry.addType(new ArrowTypeNode(argEffectList, this.type));

        //res.addAll(body.checkEffects(env));
        env.symTable.remove(env.nestingLevel);
        env.nestingLevel--;
        return res;
    }


    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, env.offset--);

        if (hm.put(ID, entry) != null) {
            res.add(new SemanticError("Fun id " + ID + " already declared"));
        } else {
            env.nestingLevel++;
            HashMap<String, STentry> hmn = new HashMap<>();

            if (hmn.put(ID, entry) != null) {
                res.add(new SemanticError("Fun id " + ID + " already declared"));
            }

            env.symTable.add(hmn);

            ArrayList<ArgNode> argTypes = new ArrayList<>();
            int argOffset = 1;

            for (ArgNode arg : args) {
                argTypes.add(arg);

                if (hmn.put(arg.getId(), new STentry(env.nestingLevel, arg.getType(), argOffset++)) != null) {
                    res.add(new SemanticError("Parameter id " + arg.getId() + " already declared"));
                }

            }

            entry.addType(new ArrowTypeNode(argTypes, type));

            /*
            if (args.size() > 0) {
                env.offset = -2;

                for (Node n : args) {
                    res.addAll(n.checkSemantics(env));
                }
            }
            */

            res.addAll(body.checkSemantics(env));
            env.symTable.remove(env.nestingLevel);
            env.nestingLevel--;
        }

        return res;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}