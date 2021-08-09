package ast;

import java.util.ArrayList;

import parser.*;
import parser.SimpLanPlusParser.*;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {

    @Override
    public Node visitBlock(BlockContext ctx) {
        BlockNode res;

        ArrayList<Node> declarations = new ArrayList<>();

        ArrayList<Node> statements = new ArrayList<>();

        for (DeclarationContext dc : ctx.declaration()) {
            declarations.add(visit(dc));
        }

        for (StatementContext st : ctx.statement()) {
            statements.add(visit(st));
        }

        res = new BlockNode(declarations, statements);

        return res;
    }

    @Override
    public Node visitDecVar(DecVarContext ctx) {

        Node typeNode = visit(ctx.type());

        Node exp = null;
        ExpContext tmp = ctx.exp();
        if (tmp != null) {
            exp = visit(tmp);
        }

        return new DecVarNode(ctx.ID().getText(), typeNode, exp);
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
    public Node visitDecFun(DecFunContext ctx) {

        VoidTypeNode voidType = null;
        DecFunNode res = new DecFunNode(null, null, null);

        TypeContext tmp = ctx.type();

        if (tmp == null) {
            res = new DecFunNode(ctx.ID().getText(), new VoidTypeNode(), visit(ctx.block()));
        } else {
            res = new DecFunNode(ctx.ID().getText(), visit(ctx.type()), visit(ctx.block()));
        }

        for (ArgContext vc : ctx.arg())
            res.addArg(new ArgNode(vc.ID().getText(), visit(vc.type())));

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
    public Node visitAssignment(AssignmentContext ctx) {
        Node lhs = visit(ctx.lhs());
        Node exp = visit(ctx.exp());

        return new AsgNode(lhs, exp);
    }

    @Override
    public Node visitLhs(LhsContext ctx) {
        Integer nestLev;
        if (ctx.getText().indexOf("^") != -1) {
            nestLev = ctx.getText().length() - ctx.getText().indexOf("^");
        } else nestLev = 0;
        return new LhsNode(ctx.getText().substring(0, ctx.getText().length() - nestLev), nestLev);
    }

    @Override
    public Node visitPrint(PrintContext ctx) {
        return new PrintNode(visit(ctx.exp()));
    }

    @Override
    public Node visitRet(RetContext ctx) {
        Node exp = null;
        ExpContext tmp = ctx.exp();
        if (tmp != null) {
            exp = visit(tmp);
        }

        return new RetNode(exp);
    }

    @Override
    public Node visitIte(IteContext ctx) {
        IteNode res;

        Node condExp = visit(ctx.exp());
        Node thenExp = visit(ctx.statement(0));
        Node elseExp = null;

        if (ctx.statement().size() > 1) {
            elseExp = visit(ctx.statement(1));
        }

        res = new IteNode(condExp, thenExp, elseExp);

        return res;
    }

    @Override
    public Node visitCall(CallContext ctx) {
        ArrayList<Node> args = new ArrayList<Node>();

        for (ExpContext exp : ctx.exp())
            args.add(visit(exp));

        return new CallNode(ctx.ID().getText(), args);
    }

    @Override
    public Node visitDeletion(DeletionContext ctx) {
        return new DeletionNode(ctx.ID().getText());
    }

    ;


    @Override
    public Node visitBaseExp(BaseExpContext ctx) {
        return new BaseExpNode(visit(ctx.exp()));
    }

    @Override
    public Node visitBinExp(BinExpContext ctx) {
        return new BinExpNode(visit(ctx.left), ctx.op, visit(ctx.right));
    }

    @Override
    public Node visitDerExp(DerExpContext ctx) {
        return new DerExpNode(visit(ctx.lhs()));
    }

    @Override
    public Node visitNewExp(NewExpContext ctx) {
        return new NewExpNode(visit(ctx.type()));
    }

    @Override
    public Node visitNegExp(NegExpContext ctx) {
        return new NegExpNode(visit(ctx.exp()));
    }

    @Override
    public Node visitCallExp(CallExpContext ctx) {
        return new CallExpNode(visit(ctx.call()));
    }

    @Override
    public Node visitNotExp(NotExpContext ctx) {
        return new NotExpNode(visit(ctx.exp()));
    }

    @Override
    public Node visitValExp(ValExpContext ctx) {
        return new IntNode(Integer.parseInt(ctx.NUMBER().getText()), 0);
    }

    @Override
    public Node visitBoolExp(BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()), 0);
    }

}
