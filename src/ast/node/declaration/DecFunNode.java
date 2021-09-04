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

/**
 * Function declaration node.
 *
 * decFun : (type | 'void') ID '(' (arg (',' arg)*)? ')' block ;
 */

public class DecFunNode implements Node {

    private String ID;
    private Node type;
    private ArrayList<ArgNode> args;
    private Node body;
    private int funUniqueLabel;

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
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        // Look up function identifier in the current scope
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, 0);

        if (hm.put(ID, entry) != null) {
            res.add(new SemanticError("Fun id " + ID + " already declared"));
        } else {
            /*
             * Create a new environment from scratch so that global variables will not be
             * accessed
             */
            Environment funEnv = new Environment();
            funEnv.nestingLevel = env.nestingLevel;
            /*
             * Functions activation record has parameters over(at higher memory addresses)
             * frame pointer memory address, so offset for actual parameters must start from
             * 1
             */
            funEnv.offset++;

            /*
             * As discussed in project report, hmFun is the SIGMA FUN hash map,
             * so it will be filled with all function definitions visible from
             * the scope of ID function
             */
            HashMap<String, STentry> hmFun = new HashMap<>();

            /*
             * Function environment is now empty but we can have nested declaration of
             * function, so to build new raw environment correctly we add empty hash maps
             * considering the nesting level at which function is declared
             */
            for (int i = env.nestingLevel; i > 0; i--) {
                funEnv.symTable.add(new HashMap<>());
            }

            /*
             * Adding ID to hmFun allows recursion
             */
            if (hmFun.put(ID, entry) != null) {
                res.add(new SemanticError("Fun id " + ID + " already declared"));
            }

            int getFunEnv = env.nestingLevel;
            while (getFunEnv >= 0) {
                for (Map.Entry<String, STentry> funEntry : env.symTable.get(getFunEnv).entrySet()) {
                    if (funEntry.getValue().getType() instanceof ArrowTypeNode) {
                        /*
                         * Since we can have different functions with the same identifier
                         * declared in different scopes, we consider the first definition we
                         * found from the current scope as the valid one (shadowing)
                         */
                        if (!hmFun.containsKey(funEntry.getKey())) {
                            hmFun.put(funEntry.getKey(), funEntry.getValue());
                        }
                    }
                }
                getFunEnv--;
            }

            // May we can change this part and put it before hmFun definition
            entry.addType(new ArrowTypeNode(args, this.type));

            hmFun.put(ID, entry);

            funEnv.symTable.add(hmFun);

            // Increment new environment nesting level and insert in a new hash map the formal parameters
            funEnv.nestingLevel++;
            HashMap<String, STentry> hmArg = new HashMap<>();

            funEnv.symTable.add(hmArg);

            for (ArgNode arg : args) {
                if (hmArg.put(arg.getId(), new STentry(funEnv.nestingLevel, arg.getType(), funEnv.offset++)) != null) {
                    res.add(new SemanticError("Parameter id " + arg.getId() + " already declared"));
                }
            }

            // This label will be used in code generation
            this.funUniqueLabel = ((ArrowTypeNode) entry.getType()).getFunUniqueLabel();

            /*
             * In function activation record local variables are located under (at lower memory addresses
             * in relation to the frame pointer memory address) return address, so their offset will start from
             * -2
             */
            funEnv.offset = -2;

            res.addAll(body.checkSemantics(funEnv));
            funEnv.symTable.remove(funEnv.nestingLevel);
            funEnv.nestingLevel--;
        }

        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node bodyTmp = body.typeCheck(typeErr);
        if (this.type.getPointLevel() == 0) {
            if (!SimpLanPlusLib.isSubtype(this.type, bodyTmp)) {
                typeErr.add(
                        new SemanticError("Mismatching return types <function = " + this.type.getClass().getSimpleName()
                                + ", body = " + bodyTmp.getClass().getSimpleName() + ">"));
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
        STentry entry = new STentry(env.nestingLevel, 0);

        // Here we can skip checks made during checkSemantics
        hm.put(ID, entry);

        effectEnv.nestingLevel++;
        HashMap<String, STentry> hmFun = new HashMap<>();
        effectEnv.symTable.add(effectEnv.nestingLevel, hmFun);

       /* for (int i = env.nestingLevel; i > 0; i--) {
            effectEnv.symTable.add(new HashMap<>());
        }*/

        int getFunEnv = env.nestingLevel;
        while (getFunEnv >= 0) {
            for (Map.Entry<String, STentry> funEntry : env.symTable.get(getFunEnv).entrySet()) {
                if (funEntry.getValue().getType() instanceof ArrowTypeNode) {
                    if (!hmFun.containsKey(funEntry.getKey())) {
                        hmFun.put(funEntry.getKey(), funEntry.getValue());
                    }
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
                hmn.put(arg.getId(), new STentry(effectEnv.nestingLevel,
                        new IntTypeNode(arg.getType().getPointLevel(), arg.getType().getStatus()), argOffset++));
            } else {
                hmn.put(arg.getId(), new STentry(effectEnv.nestingLevel,
                        new BoolTypeNode(arg.getType().getPointLevel(), arg.getType().getStatus()), argOffset++));
            }

        }

        // fixPoint Begin
        ArrayList<ArgNode> sigma0;
        ArrayList<ArgNode> sigma1 = args;

        /**
         * Since fixPoint ends doing an extra loop, we use an auxiliary list of errors
         * that will be added to the res full collection of semantic errors only after
         * fixPoint is finished. This way we avoid reporting errors twice
         */
        ArrayList<SemanticError> fixPointErrors;
        boolean checkFixPoint;

        // All formal parameters starts from status READWRITE
        for (ArgNode arg : args) {
            effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().setStatus(Status.READWRITE);
        }

        do {

            fixPointErrors = new ArrayList<>();

            checkFixPoint = false;

            sigma0 = new ArrayList<>();

            // Save sigma1 in sigma0 to make comparison after fixPoint loop
            for (int i = 0; i < sigma1.size(); i++) {
                if (sigma1.get(i).getType() instanceof IntTypeNode)
                    sigma0.add(new ArgNode(sigma1.get(i).getId(),
                            new IntTypeNode(sigma1.get(i).getPointLevel(), sigma1.get(i).getType().getStatus())));
                else if (sigma1.get(i).getType() instanceof BoolTypeNode)
                    sigma0.add(new ArgNode(sigma1.get(i).getId(),
                            new BoolTypeNode(sigma1.get(i).getPointLevel(), sigma1.get(i).getType().getStatus())));
            }

            // With body checkEffects, status of formal parameters in symbol table changes
            fixPointErrors.addAll(body.checkEffects(effectEnv));

            // Swap local variables status with args if local vars are formal parameters
            for (ArgNode arg : args) {
                Status localVar = effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().getStatus();
                effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().setStatus(Status.READWRITE);
                ArrayList<ArgNode> tmpArgList = ((ArrowTypeNode) effectEnv.symTable.get(effectEnv.nestingLevel - 1)
                        .get(this.ID).getType()).getArgList();
                for (ArgNode arrowArg : tmpArgList) {
                    if (effectEnv.symTable.get(effectEnv.nestingLevel).containsKey(arrowArg.getId())
                            && arrowArg.getId() == arg.getId()) {
                        arrowArg.getType().setStatus(localVar);
                    }
                }
            }

            // Now we save the effects applied by the function body in sigma1
            sigma1 = new ArrayList<>();

            for (ArgNode a : ((ArrowTypeNode) effectEnv.symTable.get(effectEnv.nestingLevel - 1).get(this.ID).getType())
                    .getArgList()) {
                ArgNode tmpArg = new ArgNode(a.getId(), a.getType());
                sigma1.add(tmpArg);
            }

            // Compare sigma0 and sigma1 to determine fixPoint algorithm stopping condition
            for (int idx = 0; idx < sigma0.size(); idx++) {
                if (sigma0.get(idx).getType().getStatus() != sigma1.get(idx).getType().getStatus()) {
                    checkFixPoint = true;
                    break;
                }
            }

        } while (checkFixPoint);

        // fixPoint End

        entry.addType(new ArrowTypeNode(sigma1, this.type));

        res.addAll(fixPointErrors);
        effectEnv.symTable.remove(effectEnv.nestingLevel);
        effectEnv.nestingLevel--;
        return res;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        String str = indent + "DecFun: " + ID + "\n" + type.toPrint(indent + "\t");
        builder.append(str);
        for (ArgNode arg : args) {
            builder.append(arg.toPrint(indent + "\t"));
        }
        builder.append(body.toPrint(indent + "\t"));
        return builder.toString();
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        // This first label is used to skip code execution when interpreter reads bytecode
        Label label = new Label();
        /*
         *  This second label is used inside return node. Once we found a return node during execution, we
         *  jump to the end of function body code generation in order to handle stack and registers correctly
         */
        Label labelReturn = new Label();

        env.incrementNestingLevel();
        env.setLabel(labelReturn.getLabel());

        // Count pops from stack for function arguments
        StringBuilder popArgs = new StringBuilder();
        for (int i = 0; i < args.size(); i++)
            popArgs.append("pop\n");

        // Count pops from stack for function local variables
        StringBuilder popLocal = new StringBuilder();
        ArrayList<Node> decList = ((BlockNode) body).getDecList();
        for (int i = 0; i < decList.size(); i++) {
            if (!(decList.get(i) instanceof DecFunNode)) {
                popLocal.append("pop\n");
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("b ").append(label.getLabel()).append("\n")
                .append("__").append(this.ID).append(funUniqueLabel).append("_:\n")
                .append("cal\n")
                .append("push $al\n")
                .append("cfp\n")
                .append("push $ra\n")
                .append(this.body.codeGeneration(env))
                // when we find a return node in the body code generation, we jump here
                .append(labelReturn.getLabel()).append(":\n")
                .append(popLocal)
                .append("lw $a0 $sp\n")
                // return execution to the instruction after function call
                .append("sra\n")
                .append("pop\n")
                .append("lw $a0 $sp\n")
                // restore old frame pointer
                .append("sfp\n")
                .append("pop\n")
                .append(popArgs)
                // add return value to $a0
                .append("lrv\n")
                .append("jr\n")
                .append(label.getLabel())
                .append(":\n");
        env.decrementNestingLevel();
        env.removeLabel();
        return builder.toString();
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public void setStatus(Status status) {
    }

}