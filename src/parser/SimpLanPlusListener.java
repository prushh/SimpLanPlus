// Generated from C:/Users/Prush/Documents/Magistrale/Compilatori_Interpreti/Project/SimpLanPlus/src/parser\SimpLanPlus.g4 by ANTLR 4.9.1
package parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpLanPlusParser}.
 */
public interface SimpLanPlusListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SimpLanPlusParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SimpLanPlusParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SimpLanPlusParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SimpLanPlusParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(SimpLanPlusParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(SimpLanPlusParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#decFun}.
	 * @param ctx the parse tree
	 */
	void enterDecFun(SimpLanPlusParser.DecFunContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#decFun}.
	 * @param ctx the parse tree
	 */
	void exitDecFun(SimpLanPlusParser.DecFunContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#decVar}.
	 * @param ctx the parse tree
	 */
	void enterDecVar(SimpLanPlusParser.DecVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#decVar}.
	 * @param ctx the parse tree
	 */
	void exitDecVar(SimpLanPlusParser.DecVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SimpLanPlusParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SimpLanPlusParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(SimpLanPlusParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(SimpLanPlusParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SimpLanPlusParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SimpLanPlusParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#lhs}.
	 * @param ctx the parse tree
	 */
	void enterLhs(SimpLanPlusParser.LhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#lhs}.
	 * @param ctx the parse tree
	 */
	void exitLhs(SimpLanPlusParser.LhsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#deletion}.
	 * @param ctx the parse tree
	 */
	void enterDeletion(SimpLanPlusParser.DeletionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#deletion}.
	 * @param ctx the parse tree
	 */
	void exitDeletion(SimpLanPlusParser.DeletionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(SimpLanPlusParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(SimpLanPlusParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#ret}.
	 * @param ctx the parse tree
	 */
	void enterRet(SimpLanPlusParser.RetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#ret}.
	 * @param ctx the parse tree
	 */
	void exitRet(SimpLanPlusParser.RetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#ite}.
	 * @param ctx the parse tree
	 */
	void enterIte(SimpLanPlusParser.IteContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#ite}.
	 * @param ctx the parse tree
	 */
	void exitIte(SimpLanPlusParser.IteContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLanPlusParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(SimpLanPlusParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLanPlusParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(SimpLanPlusParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code baseExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBaseExp(SimpLanPlusParser.BaseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code baseExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBaseExp(SimpLanPlusParser.BaseExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBinExp(SimpLanPlusParser.BinExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBinExp(SimpLanPlusParser.BinExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code derExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDerExp(SimpLanPlusParser.DerExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code derExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDerExp(SimpLanPlusParser.DerExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNewExp(SimpLanPlusParser.NewExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNewExp(SimpLanPlusParser.NewExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterValExp(SimpLanPlusParser.ValExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitValExp(SimpLanPlusParser.ValExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNegExp(SimpLanPlusParser.NegExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNegExp(SimpLanPlusParser.NegExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBoolExp(SimpLanPlusParser.BoolExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBoolExp(SimpLanPlusParser.BoolExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCallExp(SimpLanPlusParser.CallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCallExp(SimpLanPlusParser.CallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNotExp(SimpLanPlusParser.NotExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNotExp(SimpLanPlusParser.NotExpContext ctx);
}