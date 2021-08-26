// Generated from C:/Users/bruki/IdeaProjects/SimpLanPlus/src/parser\SVM.g4 by ANTLR 4.9.1
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
			PUSH = 1, POP = 2, ADD = 3, ADDI = 4, SUB = 5, MULT = 6, DIV = 7, STOREW = 8, STOREI = 9,
			LOADW = 10, LOADI = 11, BRANCH = 12, BRANCHEQ = 13, BRANCHLESSEQ = 14, LESS = 15,
			LESSEQ = 16, EQ = 17, NEQ = 18, AND = 19, OR = 20, JR = 21, JAL = 22, LOADRA = 23, STORERA = 24,
			LOADRV = 25, STORERV = 26, LOADFP = 27, STOREFP = 28, COPYFP = 29, COPYAL = 30, LOADHP = 31,
			STOREHP = 32, PRINT = 33, HALT = 34, COL = 35, LABEL = 36, NUMBER = 37, REGISTER = 38,
			A0 = 39, T0 = 40, SP = 41, RA = 42, FP = 43, AL = 44, WHITESP = 45, ERR = 46;
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
				"'sw'", "'si'", "'lw'", "'li'", "'b'", "'beq'", "'bleq'", "'less'", "'leq'",
				"'eq'", "'neq'", "'and'", "'or'", "'jr'", "'jal'", "'lra'", "'sra'",
				"'lrv'", "'srv'", "'lfp'", "'sfp'", "'cfp'", "'cal'", "'lhp'", "'shp'",
				"'print'", "'halt'", "':'", null, null, null, "'$a0'", "'$t0'", "'$sp'",
				"'$ra'", "'$fp'", "'$al'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[]{
				null, "PUSH", "POP", "ADD", "ADDI", "SUB", "MULT", "DIV", "STOREW", "STOREI",
				"LOADW", "LOADI", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "LESS", "LESSEQ",
				"EQ", "NEQ", "AND", "OR", "JR", "JAL", "LOADRA", "STORERA", "LOADRV",
				"STORERV", "LOADFP", "STOREFP", "COPYFP", "COPYAL", "LOADHP", "STOREHP",
				"PRINT", "HALT", "COL", "LABEL", "NUMBER", "REGISTER", "A0", "T0", "SP",
				"RA", "FP", "AL", "WHITESP", "ERR"
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
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << ADDI) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << STOREI) | (1L << LOADW) | (1L << LOADI) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << LESS) | (1L << LESSEQ) | (1L << EQ) | (1L << NEQ) | (1L << AND) | (1L << OR) | (1L << JR) | (1L << JAL) | (1L << LOADRA) | (1L << STORERA) | (1L << LOADRV) | (1L << STORERV) | (1L << LOADFP) | (1L << STOREFP) | (1L << COPYFP) | (1L << COPYAL) | (1L << LOADHP) | (1L << STOREHP) | (1L << PRINT) | (1L << HALT) | (1L << LABEL))) != 0)) {
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
		public Token source;
		public Token e1;
		public Token e2;
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

		public TerminalNode STOREI() {
			return getToken(SVMParser.STOREI, 0);
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

		public TerminalNode COPYAL() {
			return getToken(SVMParser.COPYAL, 0);
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
				setState(104);
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
					}
					break;
					case 11: {
						setState(39);
						match(STOREI);
						setState(40);
						((InstructionContext) _localctx).val = match(NUMBER);
						setState(41);
						((InstructionContext) _localctx).dest = match(REGISTER);
					}
					break;
					case 12: {
						setState(42);
						match(LOADW);
						setState(43);
						((InstructionContext) _localctx).val = match(REGISTER);
						setState(44);
						((InstructionContext) _localctx).source = match(REGISTER);
					}
					break;
					case 13: {
						setState(45);
						match(LOADI);
						setState(46);
						((InstructionContext) _localctx).val = match(REGISTER);
						setState(47);
						((InstructionContext) _localctx).source = match(NUMBER);
					}
					break;
					case 14: {
						setState(48);
						((InstructionContext) _localctx).l = match(LABEL);
						setState(49);
						match(COL);
					}
					break;
					case 15: {
						setState(50);
						match(BRANCH);
						setState(51);
						((InstructionContext) _localctx).l = match(LABEL);
					}
					break;
					case 16: {
						setState(52);
						match(BRANCHEQ);
						setState(53);
						((InstructionContext) _localctx).l = match(LABEL);
						setState(54);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(55);
						((InstructionContext) _localctx).e2 = match(REGISTER);
					}
					break;
					case 17: {
						setState(56);
						match(BRANCHLESSEQ);
						setState(57);
						((InstructionContext) _localctx).l = match(LABEL);
						setState(58);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(59);
						((InstructionContext) _localctx).e2 = match(REGISTER);
					}
					break;
					case 18: {
						setState(60);
						match(LESS);
						setState(61);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(62);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(63);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 19: {
						setState(64);
						match(LESSEQ);
						setState(65);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(66);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(67);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 20: {
						setState(68);
						match(EQ);
						setState(69);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(70);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(71);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 21: {
						setState(72);
						match(NEQ);
						setState(73);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(74);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(75);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 22: {
						setState(76);
						match(AND);
						setState(77);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(78);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(79);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 23: {
						setState(80);
						match(OR);
						setState(81);
						((InstructionContext) _localctx).e1 = match(REGISTER);
						setState(82);
						((InstructionContext) _localctx).e2 = match(REGISTER);
						setState(83);
						((InstructionContext) _localctx).res = match(REGISTER);
					}
					break;
					case 24: {
						setState(84);
						match(JAL);
						setState(85);
						((InstructionContext) _localctx).l = match(LABEL);
					}
					break;
					case 25: {
						setState(86);
						match(JR);
					}
					break;
					case 26: {
						setState(87);
						match(LOADRA);
					}
					break;
					case 27: {
						setState(88);
						match(STORERA);
					}
					break;
					case 28: {
						setState(89);
						match(LOADRV);
					}
					break;
					case 29: {
						setState(90);
						match(STORERV);
					}
					break;
					case 30: {
						setState(91);
						match(LOADFP);
					}
					break;
					case 31: {
						setState(92);
						match(STOREFP);
					}
					break;
					case 32: {
						setState(93);
						match(COPYFP);
					}
					break;
					case 33: {
						setState(94);
						match(COPYAL);
					}
					break;
					case 34: {
						setState(95);
						match(LOADHP);
						setState(96);
						((InstructionContext) _localctx).dest = match(REGISTER);
						setState(97);
						((InstructionContext) _localctx).hp = match(REGISTER);
					}
					break;
					case 35: {
						setState(98);
						match(STOREHP);
						setState(99);
						((InstructionContext) _localctx).source = match(REGISTER);
						setState(100);
						((InstructionContext) _localctx).hp = match(REGISTER);
					}
					break;
					case 36: {
						setState(101);
						match(PRINT);
						setState(102);
						((InstructionContext) _localctx).val = match(REGISTER);
					}
					break;
					case 37: {
						setState(103);
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
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\60m\4\2\t\2\4\3\t" +
					"\3\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3" +
					"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3k\n\3" +
					"\3\3\2\2\4\2\4\2\2\2\u008f\2\t\3\2\2\2\4j\3\2\2\2\6\b\5\4\3\2\7\6\3\2" +
					"\2\2\b\13\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2\f\r" +
					"\7\3\2\2\rk\7\'\2\2\16\17\7\3\2\2\17k\7&\2\2\20\21\7\3\2\2\21k\7(\2\2" +
					"\22k\7\4\2\2\23\24\7\5\2\2\24\25\7(\2\2\25\26\7(\2\2\26k\7(\2\2\27\30" +
					"\7\6\2\2\30\31\7(\2\2\31k\7\'\2\2\32\33\7\7\2\2\33\34\7(\2\2\34\35\7(" +
					"\2\2\35k\7(\2\2\36\37\7\b\2\2\37 \7(\2\2 !\7(\2\2!k\7(\2\2\"#\7\t\2\2" +
					"#$\7(\2\2$%\7(\2\2%k\7(\2\2&\'\7\n\2\2\'(\7(\2\2(k\7(\2\2)*\7\13\2\2*" +
					"+\7\'\2\2+k\7(\2\2,-\7\f\2\2-.\7(\2\2.k\7(\2\2/\60\7\r\2\2\60\61\7(\2" +
					"\2\61k\7\'\2\2\62\63\7&\2\2\63k\7%\2\2\64\65\7\16\2\2\65k\7&\2\2\66\67" +
					"\7\17\2\2\678\7&\2\289\7(\2\29k\7(\2\2:;\7\20\2\2;<\7&\2\2<=\7(\2\2=k" +
					"\7(\2\2>?\7\21\2\2?@\7(\2\2@A\7(\2\2Ak\7(\2\2BC\7\22\2\2CD\7(\2\2DE\7" +
					"(\2\2Ek\7(\2\2FG\7\23\2\2GH\7(\2\2HI\7(\2\2Ik\7(\2\2JK\7\24\2\2KL\7(\2" +
					"\2LM\7(\2\2Mk\7(\2\2NO\7\25\2\2OP\7(\2\2PQ\7(\2\2Qk\7(\2\2RS\7\26\2\2" +
					"ST\7(\2\2TU\7(\2\2Uk\7(\2\2VW\7\30\2\2Wk\7&\2\2Xk\7\27\2\2Yk\7\31\2\2" +
					"Zk\7\32\2\2[k\7\33\2\2\\k\7\34\2\2]k\7\35\2\2^k\7\36\2\2_k\7\37\2\2`k" +
					"\7 \2\2ab\7!\2\2bc\7(\2\2ck\7(\2\2de\7\"\2\2ef\7(\2\2fk\7(\2\2gh\7#\2" +
					"\2hk\7(\2\2ik\7$\2\2j\f\3\2\2\2j\16\3\2\2\2j\20\3\2\2\2j\22\3\2\2\2j\23" +
					"\3\2\2\2j\27\3\2\2\2j\32\3\2\2\2j\36\3\2\2\2j\"\3\2\2\2j&\3\2\2\2j)\3" +
					"\2\2\2j,\3\2\2\2j/\3\2\2\2j\62\3\2\2\2j\64\3\2\2\2j\66\3\2\2\2j:\3\2\2" +
					"\2j>\3\2\2\2jB\3\2\2\2jF\3\2\2\2jJ\3\2\2\2jN\3\2\2\2jR\3\2\2\2jV\3\2\2" +
					"\2jX\3\2\2\2jY\3\2\2\2jZ\3\2\2\2j[\3\2\2\2j\\\3\2\2\2j]\3\2\2\2j^\3\2" +
					"\2\2j_\3\2\2\2j`\3\2\2\2ja\3\2\2\2jd\3\2\2\2jg\3\2\2\2ji\3\2\2\2k\5\3" +
					"\2\2\2\4\tj";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}