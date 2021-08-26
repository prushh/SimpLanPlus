package ast;

import Interpreter.ExecuteVM;
import parser.SVMBaseVisitor;
import parser.SVMLexer;
import parser.SVMParser;

import java.util.HashMap;

import static util.SimpLanPlusLib.getRegister;


public class SVMVisitorImpl extends SVMBaseVisitor<Void> {

    public int[] code = new int[ExecuteVM.CODESIZE];
    private int i = 0;
    private HashMap<String, Integer> labelAdd = new HashMap<String, Integer>();
    private HashMap<Integer, String> labelRef = new HashMap<Integer, String>();

    @Override
    public Void visitAssembly(SVMParser.AssemblyContext ctx) {
        visitChildren(ctx);
        for (Integer refAdd : labelRef.keySet()) {
            code[refAdd] = labelAdd.get(labelRef.get(refAdd));
        }
        return null;
    }

    @Override
    public Void visitInstruction(SVMParser.InstructionContext ctx) {
        switch (ctx.getStart().getType()) {
            case SVMLexer.PUSH:
                if (ctx.n != null) {
                    code[i++] = SVMParser.PUSH;
                    code[i++] = Integer.parseInt(ctx.n.getText());
                } else if (ctx.l != null) {
                    code[i++] = SVMParser.PUSH;
                    labelRef.put(i++, ctx.l.getText());
                } else {
                    code[i++] = SVMParser.PUSH;
                    code[i++] = getRegister(ctx.r);
                }
                break;
            case SVMLexer.POP:
                code[i++] = SVMParser.POP;
                break;
            case SVMLexer.ADD:
                code[i++] = SVMParser.ADD;
                code[i++] = getRegister(ctx.r1);
                code[i++] = getRegister(ctx.r2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.ADDI:
                code[i++] = SVMParser.ADDI;
                code[i++] = getRegister(ctx.r1);
                code[i++] = Integer.parseInt(ctx.val.getText());
                break;
            case SVMLexer.SUB:
                code[i++] = SVMParser.SUB;
                code[i++] = getRegister(ctx.r1);
                code[i++] = getRegister(ctx.r2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.MULT:
                code[i++] = SVMParser.MULT;
                code[i++] = getRegister(ctx.r1);
                code[i++] = getRegister(ctx.r2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.DIV:
                code[i++] = SVMParser.DIV;
                code[i++] = getRegister(ctx.r1);
                code[i++] = getRegister(ctx.r2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.STOREW:
                code[i++] = SVMParser.STOREW;
                code[i++] = getRegister(ctx.val);
                code[i++] = getRegister(ctx.dest);
                break;
            case SVMLexer.STOREI:
                code[i++] = SVMParser.STOREI;
                code[i++] = Integer.parseInt(ctx.val.getText());
                code[i++] = getRegister(ctx.dest);
                break;
            case SVMLexer.LOADW:
                code[i++] = SVMParser.LOADW;
                code[i++] = getRegister(ctx.val);
                code[i++] = getRegister(ctx.source);
                break;
            case SVMLexer.LOADI:
                code[i++] = SVMParser.LOADI;
                code[i++] = getRegister(ctx.val);
                code[i++] = Integer.parseInt(ctx.source.getText());
                break;
            case SVMLexer.LABEL:
                labelAdd.put(ctx.l.getText(), i);
                break;
            case SVMLexer.BRANCH:
                code[i++] = SVMParser.BRANCH;
                labelRef.put(i++, (ctx.l != null ? ctx.l.getText() : null));
                break;
            case SVMLexer.BRANCHEQ:
                code[i++] = SVMParser.BRANCHEQ;
                labelRef.put(i++, (ctx.l != null ? ctx.l.getText() : null));
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                break;
            case SVMLexer.BRANCHLESSEQ:
                code[i++] = SVMParser.BRANCHLESSEQ;
                labelRef.put(i++, (ctx.l != null ? ctx.l.getText() : null));
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                break;
            case SVMLexer.LESS:
                code[i++] = SVMParser.LESS;
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.LESSEQ:
                code[i++] = SVMParser.LESSEQ;
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.EQ:
                code[i++] = SVMParser.EQ;
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.NEQ:
                code[i++] = SVMParser.NEQ;
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.AND:
                code[i++] = SVMParser.AND;
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.OR:
                code[i++] = SVMParser.OR;
                code[i++] = getRegister(ctx.e1);
                code[i++] = getRegister(ctx.e2);
                code[i++] = getRegister(ctx.res);
                break;
            case SVMLexer.JAL:
                code[i++] = SVMParser.JAL;
                labelRef.put(i++, ctx.l.getText());
            case SVMLexer.JR:
                code[i++] = SVMParser.JR;
                code[i++] = getRegister(ctx.r);
                break;
            case SVMLexer.LOADRA:
                code[i++] = SVMParser.LOADRA;
                break;
            case SVMLexer.STORERA:
                code[i++] = SVMParser.STORERA;
                break;
            case SVMLexer.LOADRV:
                code[i++] = SVMParser.LOADRV;
                break;
            case SVMLexer.STORERV:
                code[i++] = SVMParser.STORERV;
                break;
            case SVMLexer.LOADFP:
                code[i++] = SVMParser.LOADFP;
                break;
            case SVMLexer.STOREFP:
                code[i++] = SVMParser.STOREFP;
                break;
            case SVMLexer.COPYFP:
                code[i++] = SVMParser.COPYFP;
                break;
            case SVMLexer.LOADAL:
                code[i++] = SVMParser.LOADAL;
                break;
            case SVMLexer.STOREAL:
                code[i++] = SVMParser.STOREAL;
                break;
            case SVMLexer.COPYAL:
                code[i++] = SVMParser.COPYAL;
                break;
            case SVMLexer.LOADHP:
                code[i++] = SVMParser.LOADHP;
                break;
            case SVMLexer.STOREHP:
                code[i++] = SVMParser.STOREHP;
                break;
            case SVMLexer.PRINT:
                code[i++] = SVMParser.PRINT;
                code[i++] = getRegister(ctx.val);
                break;
            case SVMLexer.HALT:
                code[i++] = SVMParser.HALT;
                break;
            default:
                break;    // Invalid instruction
        }
        return null;
    }

}
