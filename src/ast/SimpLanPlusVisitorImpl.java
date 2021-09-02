package ast;

import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import ast.node.exp.*;
import ast.node.other.ArgNode;
import ast.node.other.LhsNode;
import ast.node.statement.*;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.VoidTypeNode;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser.*;
import util.Status;

import java.util.ArrayList;

/**
 * In this class standard visit of the AST is modified so that is possible to create nodes
 * of the ast package.
 */

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {

    @Override
    public BlockNode visitBlock(BlockContext ctx) {
        BlockNode block;

        ArrayList<Node> declarations = new ArrayList<>();
        ArrayList<Node> statements = new ArrayList<>();

        for (DeclarationContext dc : ctx.declaration()) {
            declarations.add(visit(dc));
        }

        for (StatementContext st : ctx.statement()) {
            statements.add(visit(st));
        }

        block = new BlockNode(declarations, statements);

        return block;
    }

    @Override
    public DecVarNode visitDecVar(DecVarContext ctx) {
        String id = ctx.ID().getText();
        Node type = visit(ctx.type());

        Node exp = null;
        ExpContext tmp = ctx.exp();
        if (tmp != null) {
            exp = visit(tmp);
        }

        return new DecVarNode(id, type, exp);
    }

    @Override
    public Node visitType(TypeContext ctx) {
    	/*
    	 * Since we have also pointer type, when we visit a type node we must check the number
    	 * of ^. We can have both integer and boolean pointers if the node contains the text 'int'
    	 * we must delete 3 chars so that the length of the resulting string will be the point 
    	 * level.
    	 */
        int pointLevel;

        if (ctx.getText().contains("int")) {
            pointLevel = ctx.getText().substring(0, ctx.getText().length() - 3).length();
            return new IntTypeNode(pointLevel, Status.DECLARED);
        } else if (ctx.getText().contains("bool")) {
            pointLevel = ctx.getText().substring(0, ctx.getText().length() - 4).length();
            return new BoolTypeNode(pointLevel, Status.DECLARED);
        }
        return null;
    }


    @Override
    public DecFunNode visitDecFun(DecFunContext ctx) {
    	
    	// Get function identifier
        String id = ctx.ID().getText();
        
        // Get function return type
        TypeContext tmp = ctx.type();
        
        Node type;
        if (tmp != null)
            type = visit(ctx.type());
        else
            type = new VoidTypeNode(Status.DECLARED);
        
        DecFunNode res;

        BlockNode block = visitBlock(ctx.block());
        // Set flag to distinguish between inline block and function block
        block.setBlockFunction();
        for (Node st : block.getStmList()) {        	
        	// Set flag to mark return node of a function
            if (st instanceof RetNode) {
                ((RetNode) st).setFunctionReturn(block.getBlockFunction());
            }
        }

        res = new DecFunNode(id, type, block);
        
        // Load formal parameters in the new DecFunNode
        for (ArgContext vc : ctx.arg()) {
            id = vc.ID().getText();
            type = visit(vc.type());
            res.addArg(new ArgNode(id, type));
        }

        return res;
    }

    @Override
    public Node visitStatement(StatementContext ctx) {
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        } else if (ctx.deletion() != null) {
            return visit(ctx.deletion());
        } else if (ctx.print() != null) {
            return visit(ctx.print());
        } else if (ctx.ret() != null) {
            return visit(ctx.ret());
        } else if (ctx.ite() != null) {
            return visit(ctx.ite());
        } else if (ctx.call() != null) {
            return visit(ctx.call());
        } else if (ctx.block() != null) {
            return visit(ctx.block());
        }

        return null;
    }

    @Override
    public AsgNode visitAssignment(AssignmentContext ctx) {
        Node exp = visit(ctx.exp());
        LhsNode lhs = visitLhs(ctx.lhs());

        return new AsgNode(lhs, exp);
    }

    @Override
    public LhsNode visitLhs(LhsContext ctx) {
    	
    	/* Since we can have pointers, we count ^ as in visitType, but here ^ are on the right
    	* of the variable. We will save this information in the LhsNode so that deuring semantic
    	* analysis we can verify the usage of pointers.
    	*/
        int nestLev;
        if (ctx.getText().contains("^")) {
            nestLev = ctx.getText().length() - ctx.getText().indexOf("^");
        } else {
            nestLev = 0;
        }

        String id = ctx.getText().substring(0, ctx.getText().length() - nestLev);
        return new LhsNode(id, nestLev);
    }

    @Override
    public PrintNode visitPrint(PrintContext ctx) {
        return new PrintNode(visit(ctx.exp()));
    }

    @Override
    public RetNode visitRet(RetContext ctx) {
        Node exp = null;
        ExpContext tmp = ctx.exp();
        
        // Void functions need return ; at the end so exp could be null.
        if (tmp != null) {
            exp = visit(tmp);
        }

        return new RetNode(exp);
    }

    @Override
    public IteNode visitIte(IteContext ctx) {
        Node condExp = visit(ctx.exp());
        Node thenExp = visit(ctx.statement(0));
        
        /*
         * Set flag so that during code generation is possible to make return from if-then-else
         * statements nested in functions and inline blocks correctly.
         */
        
        if (thenExp instanceof BlockNode) {
            ((BlockNode) thenExp).setBlockIte();
        }
        
        Node elseExp = null;

        if (ctx.statement().size() > 1) {
            elseExp = visit(ctx.statement(1));
            if (elseExp instanceof BlockNode) {
                ((BlockNode) elseExp).setBlockIte();
            }
        }

        return new IteNode(condExp, thenExp, elseExp);
    }

    @Override
    public CallNode visitCall(CallContext ctx) {
        ArrayList<Node> args = new ArrayList<>();
        
        // Load actual parameters in the new CallNode
        for (ExpContext exp : ctx.exp())
            args.add(visit(exp));

        return new CallNode(ctx.ID().getText(), args);
    }

    @Override
    public DeletionNode visitDeletion(DeletionContext ctx) {
        return new DeletionNode(ctx.ID().getText());
    }

    @Override
    public BaseExpNode visitBaseExp(BaseExpContext ctx) {
        return new BaseExpNode(visit(ctx.exp()));
    }

    @Override
    public BinExpNode visitBinExp(BinExpContext ctx) {
        return new BinExpNode(visit(ctx.left), ctx.op, visit(ctx.right));
    }

    @Override
    public NewExpNode visitNewExp(NewExpContext ctx) {
        return new NewExpNode(visit(ctx.type()));
    }

    @Override
    public NegExpNode visitNegExp(NegExpContext ctx) {
        return new NegExpNode(visit(ctx.exp()));
    }

    @Override
    public DerExpNode visitDerExp(DerExpContext ctx) {
        LhsNode rhs = visitLhs(ctx.lhs());
        rhs.setRightHandSide();
        return new DerExpNode(rhs);
    }

    @Override
    public CallExpNode visitCallExp(CallExpContext ctx) {
        CallNode call = visitCall(ctx.call());
        call.setCallExp();
        return new CallExpNode(call);
    }

    @Override
    public NotExpNode visitNotExp(NotExpContext ctx) {
        return new NotExpNode(visit(ctx.exp()));
    }

    @Override
    public IntNode visitValExp(ValExpContext ctx) {
        return new IntNode(Integer.parseInt(ctx.NUMBER().getText()));
    }

    @Override
    public BoolNode visitBoolExp(BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()));
    }

}
