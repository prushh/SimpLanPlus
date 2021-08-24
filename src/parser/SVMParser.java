// Generated from C:/Users/Prush/Documents/Magistrale/Compilatori_Interpreti/Project/SimpLanPlus/src/parser\SVM.g4 by ANTLR 4.9.1
package parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMParser extends Parser {
	static {
		RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	public static final int
			PUSH = 1, POP = 2, ADD = 3, ADDI = 4, SUB = 5, MULT = 6, DIV = 7, STOREW = 8, LOADW = 9,
			LOADI = 10, BRANCH = 11, BRANCHEQ = 12, BRANCHLESSEQ = 13, LESS = 14, LESSEQ = 15,
			EQ = 16, NEQ = 17, AND = 18, OR = 19, JR = 20, JAL = 21, LOADRA = 22, STORERA = 23, LOADRV = 24,
			STORERV = 25, LOADFP = 26, STOREFP = 27, COPYFP = 28, LOADHP = 29, STOREHP = 30, PRINT = 31,
			HALT = 32, COL = 33, LABEL = 34, NUMBER = 35, REGISTER = 36, WHITESP = 37, ERR = 38;
	public static final int
			RULE_assembly = 0, RULE_instruction = 1;

	private static String[] makeRuleNames() {
		return new String[]{
				"assembly", "instruction"
		};
	}

	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[]{
				null, "'push'", "'pop'", "'add'", "'addi'", "'sub'", "'mult'", "'div'",
				"'sw'", "'lw'", "'li'", "'b'", "'beq'", "'bleq'", "'less'", "'leq'",
				"'eq'", "'neq'", "'and'", "'or'", "'jr'", "'jal'", "'lra'", "'sra'",
				"'lrv'", "'srv'", "'lfp'", "'sfp'", "'cfp'", "'lhp'", "'shp'", "'print'",
				"'halt'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[]{
				null, "PUSH", "POP", "ADD", "ADDI", "SUB", "MULT", "DIV", "STOREW", "LOADW",
				"LOADI", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "LESS", "LESSEQ", "EQ",
				"NEQ", "AND", "OR", "JR", "JAL", "LOADRA", "STORERA", "LOADRV", "STORERV",
				"LOADFP", "STOREFP", "COPYFP", "LOADHP", "STOREHP", "PRINT", "HALT",
				"COL", "LABEL", "NUMBER", "REGISTER", "WHITESP", "ERR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class AssemblyContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public AssemblyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assembly; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAssembly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAssembly(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAssembly(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblyContext assembly() throws RecognitionException {
		AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assembly);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(7);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << ADDI) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << LOADW) | (1L << LOADI) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << LESS) | (1L << LESSEQ) | (1L << EQ) | (1L << NEQ) | (1L << AND) | (1L << OR) | (1L << JR) | (1L << JAL) | (1L << LOADRA) | (1L << STORERA) | (1L << LOADRV) | (1L << STORERV) | (1L << LOADFP) | (1L << STOREFP) | (1L << COPYFP) | (1L << LOADHP) | (1L << STOREHP) | (1L << PRINT) | (1L << HALT) | (1L << LABEL))) != 0)) {
					{
						{
							setState(4);
							instruction();
						}
					}
					setState(9);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public Token n;
		public Token l;
		public Token r;
		public Token r1;
		public Token r2;
		public Token res;
		public Token val;
		public Token dest;
		public Token offset;
		public Token source;
		public Token e1;
		public Token e2;
		public Token ra;
		public Token rv;
		public Token fp;
		public Token stack;
		public Token frame;
		public Token hp;

		public TerminalNode PUSH() {
			return getToken(SVMParser.PUSH, 0);
		}

		public TerminalNode POP() {
			return getToken(SVMParser.POP, 0);
		}

		public TerminalNode ADD() {
			return getToken(SVMParser.ADD, 0);
		}

		public TerminalNode ADDI() {
			return getToken(SVMParser.ADDI, 0);
		}

		public TerminalNode SUB() {
			return getToken(SVMParser.SUB, 0);
		}

		public TerminalNode MULT() {
			return getToken(SVMParser.MULT, 0);
		}

		public TerminalNode DIV() {
			return getToken(SVMParser.DIV, 0);
		}

		public TerminalNode STOREW() {
			return getToken(SVMParser.STOREW, 0);
		}

		public TerminalNode LOADW() {
			return getToken(SVMParser.LOADW, 0);
		}

		public TerminalNode LOADI() {
			return getToken(SVMParser.LOADI, 0);
		}

		public TerminalNode COL() {
			return getToken(SVMParser.COL, 0);
		}

		public TerminalNode BRANCH() {
			return getToken(SVMParser.BRANCH, 0);
		}

		public TerminalNode BRANCHEQ() {
			return getToken(SVMParser.BRANCHEQ, 0);
		}

		public TerminalNode BRANCHLESSEQ() {
			return getToken(SVMParser.BRANCHLESSEQ, 0);
		}

		public TerminalNode LESS() {
			return getToken(SVMParser.LESS, 0);
		}

		public TerminalNode LESSEQ() {
			return getToken(SVMParser.LESSEQ, 0);
		}

		public TerminalNode EQ() {
			return getToken(SVMParser.EQ, 0);
		}

		public TerminalNode NEQ() {
			return getToken(SVMParser.NEQ, 0);
		}

		public TerminalNode AND() {
			return getToken(SVMParser.AND, 0);
		}

		public TerminalNode OR() {
			return getToken(SVMParser.OR, 0);
		}

		public TerminalNode JAL() {
			return getToken(SVMParser.JAL, 0);
		}

		public TerminalNode JR() {
			return getToken(SVMParser.JR, 0);
		}

		public TerminalNode LOADRA() {
			return getToken(SVMParser.LOADRA, 0);
		}

		public TerminalNode STORERA() {
			return getToken(SVMParser.STORERA, 0);
		}

		public TerminalNode LOADRV() {
			return getToken(SVMParser.LOADRV, 0);
		}

		public TerminalNode STORERV() {
			return getToken(SVMParser.STORERV, 0);
		}

		public TerminalNode LOADFP() {
			return getToken(SVMParser.LOADFP, 0);
		}

		public TerminalNode STOREFP() {
			return getToken(SVMParser.STOREFP, 0);
		}

		public TerminalNode COPYFP() {
			return getToken(SVMParser.COPYFP, 0);
		}

		public TerminalNode LOADHP() {
			return getToken(SVMParser.LOADHP, 0);
		}

		public TerminalNode STOREHP() {
			return getToken(SVMParser.STOREHP, 0);
		}

		public TerminalNode PRINT() {
			return getToken(SVMParser.PRINT, 0);
		}

		public TerminalNode HALT() {
			return getToken(SVMParser.HALT, 0);
		}

		public TerminalNode NUMBER() {
			return getToken(SVMParser.NUMBER, 0);
		}

		public TerminalNode LABEL() {
			return getToken(SVMParser.LABEL, 0);
		}

		public List<TerminalNode> REGISTER() {
			return getTokens(SVMParser.REGISTER);
		}

		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}

		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_instruction;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SVMListener) ((SVMListener) listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SVMListener) ((SVMListener) listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(117);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
					case 1: {
						setState(10);
						match(PUSH);
						setState(11);
						((InstructionContext) _localctx).n = match(NUMBER);
					}
					break;
					case 2: {
						setState(12);
						match(PUSH);
						setState(13);
						((InstructionContext) _localctx).l = match(LABEL);
					}
					break;
					case 3: {
						setState(14);
						match(PUSH);
						setState(15);
						((InstructionContext) _localctx).r = match(REGISTER);
					}
					break;
					case 4: {
						setState(16);
						match(POP);
					}
					break;
					case 5: {
						setState(17);
						match(ADD);
						setState(18);
						((InstructionContext) _localctx).r1 = match(REGISTER);
						setState(19);
						((InstructionContext) _localctx).r2 = match(REGISTER);
						setState(20);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 6: {
						setState(21);
						match(ADDI);
						setState(22);
						((InstructionContext) _localctx).r1 = match(REGISTER);
						setState(23);
						((InstructionContext) _localctx).val = match(NUMBER);
					}
					break;
					case 7: {
						setState(24);
						match(SUB);
						setState(25);
						((InstructionContext) _localctx).r1 = match(REGISTER);
						setState(26);
						((InstructionContext) _localctx).r2 = match(REGISTER);
						setState(27);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 8: {
						setState(28);
						match(MULT);
						setState(29);
						((InstructionContext) _localctx).r1 = match(REGISTER);
						setState(30);
						((InstructionContext) _localctx).r2 = match(REGISTER);
						setState(31);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 9: {
						setState(32);
						match(DIV);
						setState(33);
						((InstructionContext) _localctx).r1 = match(REGISTER);
						setState(34);
						((InstructionContext) _localctx).r2 = match(REGISTER);
						setState(35);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 10: {
						setState(36);
						match(STOREW);
						setState(37);
						((InstructionContext) _localctx).val = match(REGISTER);
						setState(38);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(39);
						((InstructionContext) _localctx).offset = match(NUMBER);
					}
					break;
					case 11: {
						setState(40);
						match(LOADW);
						setState(41);
						((InstructionContext) _localctx).val = match(REGISTER);
						setState(42);
						((InstructionContext) _localctx).source = match(REGISTER);
						setState(43);
						((InstructionContext) _localctx).offset = match(NUMBER);
					}
					break;
					case 12: {
						setState(44);
						match(LOADI);
						setState(45);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(46);
						((InstructionContext) _localctx).val = match(NUMBER);
					}
					break;
					case 13: {
						setState(47);
						((InstructionContext) _localctx).l = match(LABEL);
						setState(48);
						match(COL);
					}
					break;
					case 14: {
						setState(49);
						match(BRANCH);
						setState(50);
						((InstructionContext) _localctx).l = match(LABEL);
					}
					break;
					case 15: {
						setState(51);
						match(BRANCHEQ);
						setState(52);
						((InstructionContext) _localctx).l = match(LABEL);
						setState(53);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(54);
						((InstructionContext) _localctx).e2 = match(REGISTER);
					}
					break;
					case 16: {
						setState(55);
						match(BRANCHLESSEQ);
						setState(56);
						((InstructionContext) _localctx).l = match(LABEL);
						setState(57);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(58);
						((InstructionContext) _localctx).e2 = match(REGISTER);
					}
					break;
					case 17: {
						setState(59);
						match(LESS);
						setState(60);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(61);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(62);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 18: {
						setState(63);
						match(LESSEQ);
						setState(64);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(65);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(66);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 19: {
						setState(67);
						match(EQ);
						setState(68);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(69);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(70);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 20: {
						setState(71);
						match(NEQ);
						setState(72);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(73);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(74);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 21: {
						setState(75);
						match(AND);
						setState(76);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(77);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(78);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 22: {
						setState(79);
						match(OR);
						setState(80);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(81);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(82);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 23: {
						setState(83);
						match(JAL);
						setState(84);
						((InstructionContext) _localctx).l = match(LABEL);
					}
					break;
					case 24: {
						setState(85);
						match(JR);
						setState(86);
						((InstructionContext) _localctx).ra = match(REGISTER);
					}
					break;
					case 25: {
						setState(87);
						match(LOADRA);
						setState(88);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(89);
						((InstructionContext) _localctx).ra = match(REGISTER);
					}
					break;
					case 26: {
						setState(90);
						match(STORERA);
						setState(91);
						((InstructionContext) _localctx).source = match(REGISTER);
						setState(92);
						((InstructionContext) _localctx).ra = match(REGISTER);
					}
					break;
					case 27: {
						setState(93);
						match(LOADRV);
						setState(94);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(95);
						((InstructionContext) _localctx).rv = match(REGISTER);
					}
					break;
					case 28: {
						setState(96);
						match(STORERV);
						setState(97);
						((InstructionContext) _localctx).source = match(REGISTER);
						setState(98);
						((InstructionContext) _localctx).rv = match(REGISTER);
					}
					break;
					case 29: {
						setState(99);
						match(LOADFP);
						setState(100);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(101);
						((InstructionContext) _localctx).fp = match(REGISTER);
					}
					break;
					case 30: {
						setState(102);
						match(STOREFP);
						setState(103);
						((InstructionContext) _localctx).source = match(REGISTER);
						setState(104);
						((InstructionContext) _localctx).fp = match(REGISTER);
					}
					break;
					case 31: {
						setState(105);
						match(COPYFP);
						setState(106);
						((InstructionContext) _localctx).stack = match(REGISTER);
						setState(107);
						((InstructionContext) _localctx).frame = match(REGISTER);
					}
					break;
					case 32: {
						setState(108);
						match(LOADHP);
						setState(109);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(110);
						((InstructionContext) _localctx).hp = match(REGISTER);
					}
					break;
					case 33: {
						setState(111);
						match(STOREHP);
						setState(112);
						((InstructionContext) _localctx).source = match(REGISTER);
						setState(113);
						((InstructionContext) _localctx).hp = match(REGISTER);
					}
					break;
					case 34: {
						setState(114);
						match(PRINT);
						setState(115);
						((InstructionContext) _localctx).val = match(REGISTER);
					}
					break;
					case 35: {
						setState(116);
						match(HALT);
					}
					break;
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(z\4\2\t\2\4\3\t\3" +
					"\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3x\n\3\3\3\2\2\4\2\4\2\2\2" +
					"\u009a\2\t\3\2\2\2\4w\3\2\2\2\6\b\5\4\3\2\7\6\3\2\2\2\b\13\3\2\2\2\t\7" +
					"\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2\f\r\7\3\2\2\rx\7%\2\2\16" +
					"\17\7\3\2\2\17x\7$\2\2\20\21\7\3\2\2\21x\7&\2\2\22x\7\4\2\2\23\24\7\5" +
					"\2\2\24\25\7&\2\2\25\26\7&\2\2\26x\7&\2\2\27\30\7\6\2\2\30\31\7&\2\2\31" +
					"x\7%\2\2\32\33\7\7\2\2\33\34\7&\2\2\34\35\7&\2\2\35x\7&\2\2\36\37\7\b" +
					"\2\2\37 \7&\2\2 !\7&\2\2!x\7&\2\2\"#\7\t\2\2#$\7&\2\2$%\7&\2\2%x\7&\2" +
					"\2&\'\7\n\2\2\'(\7&\2\2()\7&\2\2)x\7%\2\2*+\7\13\2\2+,\7&\2\2,-\7&\2\2" +
					"-x\7%\2\2./\7\f\2\2/\60\7&\2\2\60x\7%\2\2\61\62\7$\2\2\62x\7#\2\2\63\64" +
					"\7\r\2\2\64x\7$\2\2\65\66\7\16\2\2\66\67\7$\2\2\678\7&\2\28x\7&\2\29:" +
					"\7\17\2\2:;\7$\2\2;<\7&\2\2<x\7&\2\2=>\7\20\2\2>?\7&\2\2?@\7&\2\2@x\7" +
					"&\2\2AB\7\21\2\2BC\7&\2\2CD\7&\2\2Dx\7&\2\2EF\7\22\2\2FG\7&\2\2GH\7&\2" +
					"\2Hx\7&\2\2IJ\7\23\2\2JK\7&\2\2KL\7&\2\2Lx\7&\2\2MN\7\24\2\2NO\7&\2\2" +
					"OP\7&\2\2Px\7&\2\2QR\7\25\2\2RS\7&\2\2ST\7&\2\2Tx\7&\2\2UV\7\27\2\2Vx" +
					"\7$\2\2WX\7\26\2\2Xx\7&\2\2YZ\7\30\2\2Z[\7&\2\2[x\7&\2\2\\]\7\31\2\2]" +
					"^\7&\2\2^x\7&\2\2_`\7\32\2\2`a\7&\2\2ax\7&\2\2bc\7\33\2\2cd\7&\2\2dx\7" +
					"&\2\2ef\7\34\2\2fg\7&\2\2gx\7&\2\2hi\7\35\2\2ij\7&\2\2jx\7&\2\2kl\7\36" +
					"\2\2lm\7&\2\2mx\7&\2\2no\7\37\2\2op\7&\2\2px\7&\2\2qr\7 \2\2rs\7&\2\2" +
					"sx\7&\2\2tu\7!\2\2ux\7&\2\2vx\7\"\2\2w\f\3\2\2\2w\16\3\2\2\2w\20\3\2\2" +
					"\2w\22\3\2\2\2w\23\3\2\2\2w\27\3\2\2\2w\32\3\2\2\2w\36\3\2\2\2w\"\3\2" +
					"\2\2w&\3\2\2\2w*\3\2\2\2w.\3\2\2\2w\61\3\2\2\2w\63\3\2\2\2w\65\3\2\2\2" +
					"w9\3\2\2\2w=\3\2\2\2wA\3\2\2\2wE\3\2\2\2wI\3\2\2\2wM\3\2\2\2wQ\3\2\2\2" +
					"wU\3\2\2\2wW\3\2\2\2wY\3\2\2\2w\\\3\2\2\2w_\3\2\2\2wb\3\2\2\2we\3\2\2" +
					"\2wh\3\2\2\2wk\3\2\2\2wn\3\2\2\2wq\3\2\2\2wt\3\2\2\2wv\3\2\2\2x\5\3\2" +
					"\2\2\4\tw";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}