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
        if (ctx.getText().equals("int"))
            return new IntTypeNode();
        else if (ctx.getText().equals("bool"))
            return new BoolTypeNode();

        return null;
    }

    @Override
    public Node visitValExp(ValExpContext ctx) {
        return new IntNode(Integer.parseInt(ctx.NUMBER().getText()));
    }

    @Override
    public Node visitBoolExp(BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()));
    }

}
