package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.exp.BaseExpNode;
import ast.node.exp.DerExpNode;
import ast.node.other.ArgNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Function call semantic analysis and code generation.
 */

public class CallNode implements Node {

    private String ID;
    private STentry entry;
    private ArrayList<Node> args;
    private boolean isCallExp = false;

    public CallNode(String ID, ArrayList<Node> args) {
        this.ID = ID;
        this.args = args;
    }

    public void setCallExp() {
        isCallExp = true;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        int i = env.nestingLevel;
        STentry tmpEntry = null;

        // Look up function identifier in symbol table
        while (i >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(i--)).get(ID);
        }

        if (tmpEntry == null) {
            res.add(new SemanticError("Fun " + ID + " not declared"));
        } else {
            this.entry = tmpEntry;

            /*
             * If function is declared, then check semantics of each actual parameter (an
             * expression)
             */
            for (Node arg : args) {
                res.addAll(arg.checkSemantics(env));
            }
        }

        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {

        ArrowTypeNode functionType = null;

        // The entry data structure is saved by checkSemantics
        if (entry.getType() instanceof ArrowTypeNode)
            functionType = (ArrowTypeNode) entry.getType();
        else {
            typeErr.add(new SemanticError("Invocation of a non-function " + ID));
        }

        // Retrieve list of parameters saved in the entry arrow type node
        ArrayList<ArgNode> argList = functionType.getArgList();
        if (!(argList.size() == args.size())) {
            typeErr.add(new SemanticError("Wrong number of parameters in the invocation of " + ID));
        } else {

            for (int i = 0; i < args.size(); i++) {

                // Check if type actual parameters correspond to the argument list saved in the
                // entry
                Node arg_i = args.get(i).typeCheck(typeErr);
                if (!(SimpLanPlusLib.isSubtype(arg_i, argList.get(i).getType()))
                        || (arg_i.getPointLevel() != argList.get(i).getType().getPointLevel())) {
                    typeErr.add(new SemanticError(
                            "Wrong type for " + (i + 1) + "-th parameter in the invocation of " + ID));
                }
            }

            // If CallNode typeCheck is called from a CallExpNode, then return type can't be
            // void
            if (isCallExp) {
                if (functionType.getRet() instanceof VoidTypeNode) {
                    typeErr.add(new SemanticError("cannot use void function as an exp"));
                }
            } else {
                return new NullTypeNode(Status.DECLARED);
            }
        }
        return functionType.getRet();
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        // Some errors are caught by checkSemantics, so there's no need to make same
        // controls
        int i = env.nestingLevel;
        STentry tmpEntry = null;

        while (i >= 0 && tmpEntry == null) {
            tmpEntry = (env.symTable.get(i--)).get(ID);
        }

        for (Node arg : args) {
            res.addAll(arg.checkEffects(env));
        }

        // We only care about pointers effects variation during a function call,
        //  simple variables cannot be deleted.
        ArrayList<DerExpNode> pointerList = new ArrayList<>();
        for (Node arg : args) {
            Node tmp = arg;
            /*
             * From grammar, we can get pointers only from DerExpNodes (notice that all
             * actual parameters are expressions). If we have a BaseExpNode like
             * ((((pointer)))), must get the DerExpNode inside of parentheses
             */
            if (arg instanceof BaseExpNode) {
                while (((BaseExpNode) tmp).getExp() instanceof BaseExpNode) {
                    tmp = ((BaseExpNode) tmp).getExp();
                }
                tmp = ((BaseExpNode) tmp).getExp();
            }
            /*
             * Later we will update symbol table with function effects, changing an actual
             * parameter status with its corresponding fromal parameter status. To make this
             * matching between actual and formal parameter, we can't have actual arguments
             * like ((((pointer)))) and a formal parameter like pointer, se we set the former
             * with the latter
             */
            args.set(args.indexOf(arg), tmp);

            if (tmp instanceof DerExpNode) {
                DerExpNode tmpDerNode = (DerExpNode) tmp;
                int tmpNest = env.nestingLevel;
                while (env.symTable.get(tmpNest).get(tmpDerNode.getLhsNode().getID()) == null) {
                    tmpNest--;
                }
                /*
                 * Once we get all the DerExpNodes, we must check the point level with which
                 * they are dereferenced and compare it with the value in symbol table
                 */
                if (env.symTable.get(tmpNest).get(tmpDerNode.getLhsNode().getID()).getType().getPointLevel()
                        - tmpDerNode.getLhsNode().getPointLevel() > 0)
                    pointerList.add(tmpDerNode);
            }

        }

        // Now we check that every pointer is passed with status READWRITE
        for (DerExpNode arg : pointerList) {
            int tmpNest = env.nestingLevel;
            while (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()) == null) {
                tmpNest--;
            }
            if (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()).getType().getStatus() == Status.DELETED) {
                res.add(new SemanticError("Cannot pass a deleted pointer as an actual parameter"));
            } else if (env.symTable.get(tmpNest).get(arg.getLhsNode().getID()).getType().getStatus() == Status.ERROR) {
                res.add(new SemanticError("Error in accessing id --" + arg.getLhsNode().getID() + "--"));
            }
        }

        // Look up in symbol table effects associated to the function
        int tmpArrow = env.nestingLevel;
        while (env.symTable.get(tmpArrow).get(this.ID) == null) {
            tmpArrow--;
        }

        /*
         * To apply effects of the function to the pointers collected in pointerList, we
         * extract from the arrow type node written in symbol table the formal
         * parameters because they store the effects associated to the function
         */
        ArrowTypeNode arrowType = (ArrowTypeNode) env.symTable.get(tmpArrow).get(this.ID).getType();
        ArrayList<ArgNode> argsArrow = arrowType.getArgList();

        HashMap<String, ArrayList<Status>> aliasHsm = new HashMap<>();

        /*
         * While we update actual parameters (only pointers) with the effects of the
         * formal parameters, we also build a hash map for aliases. These dictionary
         * has:
         * -as keys, the identifier of the LhsNode that compose the actual
         * parameter
         * -as values, a list containing all the formal parameters that are
         * alias for that particular actual parameter
         */
        for (int idx = 0; idx < args.size(); idx++) {
            if (pointerList.contains(args.get(idx))) {
                DerExpNode arg = (DerExpNode) args.get(idx);
                ArgNode argArrow = argsArrow.get(idx);

                String idLhs = arg.getLhsNode().getID();
                int tmpNest = env.nestingLevel;
                while (env.symTable.get(tmpNest).get(idLhs) == null) {
                    tmpNest--;
                }

                // All actual parameters starts from READWRITE
                Status argStatusStart = Status.READWRITE;
                Status argStatus = env.symTable.get(tmpNest).get(idLhs).getType().getStatus();
                Status argArrowStatus = argArrow.getType().getStatus();

                /*
                 * The final status is obtained as the sequence operation between READWRITE and
                 * the effects stated by the function (effects are calculated in the function
                 * declaration)
                 */
                Status seqFinal = SimpLanPlusLib.seqStatus(argStatusStart, argArrowStatus);

                // Build alias map
                if (aliasHsm.containsKey(idLhs)) {
                    aliasHsm.get(idLhs).add(seqFinal);
                } else {
                    ArrayList<Status> tmp = new ArrayList<>();
                    tmp.add(seqFinal);
                    aliasHsm.put(idLhs, tmp);
                }

                // Update symbol table with new status for pointers
                env.symTable.get(tmpNest).get(idLhs).getType().setStatus(SimpLanPlusLib.maxStatus(seqFinal, argStatus));
            }
        }

        // Alias checking
        for (String key : aliasHsm.keySet()) {
            // Get list of alias for a particular actual parameter
            ArrayList<Status> values = aliasHsm.get(key);
            int size = values.size();
            Status finalStatus = Status.DECLARED;
            if (size > 1) {
                // If there are alias, then list will have more than an element
                Status maxLocal = Status.DECLARED;

                // Perform par between each status of alias
                for (int idx = 0; idx < size; idx++) {
                    for (int jdx = idx; jdx < size; jdx++) {
                        if (idx != jdx) {
                            Status prev = values.get(idx);
                            Status foll = values.get(jdx);
                            maxLocal = SimpLanPlusLib.maxStatus(SimpLanPlusLib.parStatus(prev, foll), maxLocal);
                        } else {
                            maxLocal = values.get(idx);
                        }
                    }
                    finalStatus = SimpLanPlusLib.maxStatus(maxLocal, finalStatus);
                }
                values.clear();
                // Empty the list and store just the result of pars
                values.add(finalStatus);
            } else {
                aliasHsm.get(key).clear();
            }
        }

        for (String key : aliasHsm.keySet()) {

            // If there's an alias, than list in the alias map will have just a value
            if (aliasHsm.get(key).size() != 0) {
                int level = env.nestingLevel;
                STentry entry = null;
                while (level >= 0 && entry == null) {
                    entry = env.symTable.get(level--).get(key);
                }
                Status max = aliasHsm.get(key).get(0);
                // Set result of pars in the symbol table
                entry.getType().setStatus(max);
            }
        }

        for (String key : aliasHsm.keySet()) {
            if (aliasHsm.get(key).size() != 0) {
                // Report potential errors due to aliasing
                if (aliasHsm.get(key).get(0) == Status.ERROR) {
                    res.add(new SemanticError("Aliasing error in function call " + this.ID + " on pointer: " + key));
                }
            }
        }

        return res;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        String str = indent + "Call: " + ID + "\n";
        builder.append(str);
        String entryStr = indent + "at nesting level " + this.entry.getNestinglevel() + " with offset "
                + this.entry.getOffset() + "\n";
        builder.append(entryStr);

        for (Node exp : args) {
            builder.append(exp.toPrint(indent + "\t"));
        }
        return builder.toString();
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        for (int i = this.args.size() - 1; i >= 0; i--) {
            builder.append(args.get(i).codeGeneration(env)).append("push $a0\n");
        }
        builder.append("jal __")
                .append(this.ID)
                .append(((ArrowTypeNode) this.entry.getType()).getFunUniqueLabel())
                .append("_\n");
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