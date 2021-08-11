package ast;

import java.util.ArrayList;

import parser.*;
import parser.SimpLanPlusParser.*;

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
        Node type = visit(ctx.type());

        Node exp = null;
        ExpContext tmp = ctx.exp();
        if (tmp != null) {
            exp = visit(tmp);
        }

        return new DecVarNode(ctx.ID().getText(), type, exp);
    }

    @Override
    public Node visitType(TypeContext ctx) {
        int nestLev;

        if (ctx.getText().contains("int")) {
            nestLev = ctx.getText().substring(0, ctx.getText().length() - 3).length();
            return new IntTypeNode(nestLev);
        } else if (ctx.getText().contains("bool")) {
            nestLev = ctx.getText().substring(0, ctx.getText().length() - 4).length();
            return new BoolTypeNode(nestLev);
        }

        return null;
    }


    @Override
    public DecFunNode visitDecFun(DecFunContext ctx) {

        VoidTypeNode voidType = null;
        Node typeNode = visit(ctx.type());
        DecFunNode res = new DecFunNode(null, null, null);

        BlockNode block = visitBlock(ctx.block());
        block.setBlockFunction();

        TypeContext tmp = ctx.type();

        if (tmp == null) {
            res = new DecFunNode(ctx.ID().getText(), new VoidTypeNode(), block);
        } else {
            res = new DecFunNode(ctx.ID().getText(), typeNode, block);
        }

        for (ArgContext vc : ctx.arg()) {
            res.addArg(new ArgNode(vc.ID().getText(), visit(vc.type())));
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
        LhsNode lhs = visitLhs(ctx.lhs());
        Node exp = visit(ctx.exp());

        return new AsgNode(lhs, exp);
    }

    @Override
    public LhsNode visitLhs(LhsContext ctx) {
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
        if (tmp != null) {
            exp = visit(tmp);
        }

        return new RetNode(exp);
    }

    @Override
    public IteNode visitIte(IteContext ctx) {
        Node condExp = visit(ctx.exp());
        Node thenExp = visit(ctx.statement(0));
        Node elseExp = null;

        if (ctx.statement().size() > 1) {
            elseExp = visit(ctx.statement(1));
        }

        return new IteNode(condExp, thenExp, elseExp);
    }

    @Override
    public CallNode visitCall(CallContext ctx) {
        ArrayList<Node> args = new ArrayList<>();

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
    public DerExpNode visitDerExp(DerExpContext ctx) {
        return new DerExpNode(visit(ctx.lhs()));
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
