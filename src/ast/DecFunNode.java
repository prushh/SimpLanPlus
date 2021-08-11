package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;
import java.util.HashMap;

public class DecFunNode implements Node {

    private String ID;
    private Node type;
    private ArrayList<Node> args;
    private Node body;

    public DecFunNode(String ID, Node type, Node body) {
        this.ID = ID;
        this.type = type;
        this.body = body;
        args = new ArrayList<>();
    }

    public void addArg(Node arg) {
        args.add(arg);
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        if (this.type.getPointLevel() == 0) {
            if (!SimpLanlib.isSubtype(this.type, body.typeCheck())) {
                System.out.println("Mismatching return types <function = " + this.type + ", body = " + body.typeCheck() + ">");
                System.exit(0);
            }
        } else {
            System.out.println("Function can not have pointer type");
            System.exit(0);
        }
        return body.typeCheck();
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

            ArrayList<Node> argTypes = new ArrayList<>();
            int argOffset = 1;

            for (Node a : args) {
                ArgNode arg = (ArgNode) a;
                argTypes.add(arg.getType());

                if (hmn.put(arg.getId(), new STentry(env.nestingLevel, arg.getType(), argOffset++)) != null) {
                    res.add(new SemanticError("Parameter id " + arg.getId() + " already declared"));
                }

            }

            entry.addType(new ArrowTypeNode(argTypes, type));

            if (args.size() > 0) {
                env.offset = -2;

                for (Node n : args) {
                    res.addAll(n.checkSemantics(env));
                }
            }

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