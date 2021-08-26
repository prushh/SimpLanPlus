// Generated from C:/Users/bruki/IdeaProjects/SimpLanPlus/src/parser\SVM.g4 by ANTLR 4.9.1
package parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMLexer extends Lexer {
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
			LOADRV = 25, STORERV = 26, LOADFP = 27, STOREFP = 28, COPYFP = 29, LOADAL = 30, STOREAL = 31,
			COPYAL = 32, LOADHP = 33, STOREHP = 34, PRINT = 35, HALT = 36, COL = 37, LABEL = 38,
			NUMBER = 39, REGISTER = 40, A0 = 41, T0 = 42, SP = 43, RA = 44, FP = 45, AL = 46, HP = 47,
			WHITESP = 48, ERR = 49;
	public static String[] channelNames = {
			"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
			"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[]{
				"PUSH", "POP", "ADD", "ADDI", "SUB", "MULT", "DIV", "STOREW", "STOREI",
				"LOADW", "LOADI", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "LESS", "LESSEQ",
				"EQ", "NEQ", "AND", "OR", "JR", "JAL", "LOADRA", "STORERA", "LOADRV",
				"STORERV", "LOADFP", "STOREFP", "COPYFP", "LOADAL", "STOREAL", "COPYAL",
				"LOADHP", "STOREHP", "PRINT", "HALT", "COL", "LABEL", "NUMBER", "REGISTER",
				"A0", "T0", "SP", "RA", "FP", "AL", "HP", "WHITESP", "ERR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[]{
				null, "'push'", "'pop'", "'add'", "'addi'", "'sub'", "'mult'", "'div'",
				"'sw'", "'si'", "'lw'", "'li'", "'b'", "'beq'", "'bleq'", "'less'", "'leq'",
				"'eq'", "'neq'", "'and'", "'or'", "'jr'", "'jal'", "'lra'", "'sra'",
				"'lrv'", "'srv'", "'lfp'", "'sfp'", "'cfp'", "'lal'", "'sal'", "'cal'",
				"'lhp'", "'shp'", "'print'", "'halt'", "':'", null, null, null, "'$a0'",
				"'$t0'", "'$sp'", "'$ra'", "'$fp'", "'$al'", "'$hp'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[]{
				null, "PUSH", "POP", "ADD", "ADDI", "SUB", "MULT", "DIV", "STOREW", "STOREI",
				"LOADW", "LOADI", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "LESS", "LESSEQ",
				"EQ", "NEQ", "AND", "OR", "JR", "JAL", "LOADRA", "STORERA", "LOADRV",
				"STORERV", "LOADFP", "STOREFP", "COPYFP", "LOADAL", "STOREAL", "COPYAL",
				"LOADHP", "STOREHP", "PRINT", "HALT", "COL", "LABEL", "NUMBER", "REGISTER",
				"A0", "T0", "SP", "RA", "FP", "AL", "HP", "WHITESP", "ERR"
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


	public int lexicalErrors=0;


	public SVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
			case 48:
				ERR_action((RuleContext) _localctx, actionIndex);
				break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.err.println("Invalid char: "+ getText()); lexicalErrors++;  
			break;
		}
	}

	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\63\u013f\b\1\4\2" +
					"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
					"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
					"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
					"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t" +
					" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t" +
					"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\3\2\3\2\3\2\3" +
					"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6" +
					"\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13" +
					"\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17" +
					"\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23" +
					"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27" +
					"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32" +
					"\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36" +
					"\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3" +
					"\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3\'\3\'\5\'" +
					"\u00f9\n\'\3\'\3\'\7\'\u00fd\n\'\f\'\16\'\u0100\13\'\3(\3(\5(\u0104\n" +
					"(\3(\3(\7(\u0108\n(\f(\16(\u010b\13(\5(\u010d\n(\3)\3)\3)\3)\3)\3)\3)" +
					"\5)\u0116\n)\3*\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3-\3-\3-\3-\3.\3.\3." +
					"\3.\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\61\6\61\u0135\n\61\r\61\16\61\u0136" +
					"\3\61\3\61\3\62\3\62\3\62\3\62\3\62\2\2\63\3\3\5\4\7\5\t\6\13\7\r\b\17" +
					"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+" +
					"\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+" +
					"U,W-Y.[/]\60_\61a\62c\63\3\2\5\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"" +
					"\"\2\u014a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2" +
					"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
					"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2" +
					"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2" +
					"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2" +
					"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2" +
					"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S" +
					"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2" +
					"\2\2\2a\3\2\2\2\2c\3\2\2\2\3e\3\2\2\2\5j\3\2\2\2\7n\3\2\2\2\tr\3\2\2\2" +
					"\13w\3\2\2\2\r{\3\2\2\2\17\u0080\3\2\2\2\21\u0084\3\2\2\2\23\u0087\3\2" +
					"\2\2\25\u008a\3\2\2\2\27\u008d\3\2\2\2\31\u0090\3\2\2\2\33\u0092\3\2\2" +
					"\2\35\u0096\3\2\2\2\37\u009b\3\2\2\2!\u00a0\3\2\2\2#\u00a4\3\2\2\2%\u00a7" +
					"\3\2\2\2\'\u00ab\3\2\2\2)\u00af\3\2\2\2+\u00b2\3\2\2\2-\u00b5\3\2\2\2" +
					"/\u00b9\3\2\2\2\61\u00bd\3\2\2\2\63\u00c1\3\2\2\2\65\u00c5\3\2\2\2\67" +
					"\u00c9\3\2\2\29\u00cd\3\2\2\2;\u00d1\3\2\2\2=\u00d5\3\2\2\2?\u00d9\3\2" +
					"\2\2A\u00dd\3\2\2\2C\u00e1\3\2\2\2E\u00e5\3\2\2\2G\u00e9\3\2\2\2I\u00ef" +
					"\3\2\2\2K\u00f4\3\2\2\2M\u00f8\3\2\2\2O\u010c\3\2\2\2Q\u0115\3\2\2\2S" +
					"\u0117\3\2\2\2U\u011b\3\2\2\2W\u011f\3\2\2\2Y\u0123\3\2\2\2[\u0127\3\2" +
					"\2\2]\u012b\3\2\2\2_\u012f\3\2\2\2a\u0134\3\2\2\2c\u013a\3\2\2\2ef\7r" +
					"\2\2fg\7w\2\2gh\7u\2\2hi\7j\2\2i\4\3\2\2\2jk\7r\2\2kl\7q\2\2lm\7r\2\2" +
					"m\6\3\2\2\2no\7c\2\2op\7f\2\2pq\7f\2\2q\b\3\2\2\2rs\7c\2\2st\7f\2\2tu" +
					"\7f\2\2uv\7k\2\2v\n\3\2\2\2wx\7u\2\2xy\7w\2\2yz\7d\2\2z\f\3\2\2\2{|\7" +
					"o\2\2|}\7w\2\2}~\7n\2\2~\177\7v\2\2\177\16\3\2\2\2\u0080\u0081\7f\2\2" +
					"\u0081\u0082\7k\2\2\u0082\u0083\7x\2\2\u0083\20\3\2\2\2\u0084\u0085\7" +
					"u\2\2\u0085\u0086\7y\2\2\u0086\22\3\2\2\2\u0087\u0088\7u\2\2\u0088\u0089" +
					"\7k\2\2\u0089\24\3\2\2\2\u008a\u008b\7n\2\2\u008b\u008c\7y\2\2\u008c\26" +
					"\3\2\2\2\u008d\u008e\7n\2\2\u008e\u008f\7k\2\2\u008f\30\3\2\2\2\u0090" +
					"\u0091\7d\2\2\u0091\32\3\2\2\2\u0092\u0093\7d\2\2\u0093\u0094\7g\2\2\u0094" +
					"\u0095\7s\2\2\u0095\34\3\2\2\2\u0096\u0097\7d\2\2\u0097\u0098\7n\2\2\u0098" +
					"\u0099\7g\2\2\u0099\u009a\7s\2\2\u009a\36\3\2\2\2\u009b\u009c\7n\2\2\u009c" +
					"\u009d\7g\2\2\u009d\u009e\7u\2\2\u009e\u009f\7u\2\2\u009f \3\2\2\2\u00a0" +
					"\u00a1\7n\2\2\u00a1\u00a2\7g\2\2\u00a2\u00a3\7s\2\2\u00a3\"\3\2\2\2\u00a4" +
					"\u00a5\7g\2\2\u00a5\u00a6\7s\2\2\u00a6$\3\2\2\2\u00a7\u00a8\7p\2\2\u00a8" +
					"\u00a9\7g\2\2\u00a9\u00aa\7s\2\2\u00aa&\3\2\2\2\u00ab\u00ac\7c\2\2\u00ac" +
					"\u00ad\7p\2\2\u00ad\u00ae\7f\2\2\u00ae(\3\2\2\2\u00af\u00b0\7q\2\2\u00b0" +
					"\u00b1\7t\2\2\u00b1*\3\2\2\2\u00b2\u00b3\7l\2\2\u00b3\u00b4\7t\2\2\u00b4" +
					",\3\2\2\2\u00b5\u00b6\7l\2\2\u00b6\u00b7\7c\2\2\u00b7\u00b8\7n\2\2\u00b8" +
					".\3\2\2\2\u00b9\u00ba\7n\2\2\u00ba\u00bb\7t\2\2\u00bb\u00bc\7c\2\2\u00bc" +
					"\60\3\2\2\2\u00bd\u00be\7u\2\2\u00be\u00bf\7t\2\2\u00bf\u00c0\7c\2\2\u00c0" +
					"\62\3\2\2\2\u00c1\u00c2\7n\2\2\u00c2\u00c3\7t\2\2\u00c3\u00c4\7x\2\2\u00c4" +
					"\64\3\2\2\2\u00c5\u00c6\7u\2\2\u00c6\u00c7\7t\2\2\u00c7\u00c8\7x\2\2\u00c8" +
					"\66\3\2\2\2\u00c9\u00ca\7n\2\2\u00ca\u00cb\7h\2\2\u00cb\u00cc\7r\2\2\u00cc" +
					"8\3\2\2\2\u00cd\u00ce\7u\2\2\u00ce\u00cf\7h\2\2\u00cf\u00d0\7r\2\2\u00d0" +
					":\3\2\2\2\u00d1\u00d2\7e\2\2\u00d2\u00d3\7h\2\2\u00d3\u00d4\7r\2\2\u00d4" +
					"<\3\2\2\2\u00d5\u00d6\7n\2\2\u00d6\u00d7\7c\2\2\u00d7\u00d8\7n\2\2\u00d8" +
					">\3\2\2\2\u00d9\u00da\7u\2\2\u00da\u00db\7c\2\2\u00db\u00dc\7n\2\2\u00dc" +
					"@\3\2\2\2\u00dd\u00de\7e\2\2\u00de\u00df\7c\2\2\u00df\u00e0\7n\2\2\u00e0" +
					"B\3\2\2\2\u00e1\u00e2\7n\2\2\u00e2\u00e3\7j\2\2\u00e3\u00e4\7r\2\2\u00e4" +
					"D\3\2\2\2\u00e5\u00e6\7u\2\2\u00e6\u00e7\7j\2\2\u00e7\u00e8\7r\2\2\u00e8" +
					"F\3\2\2\2\u00e9\u00ea\7r\2\2\u00ea\u00eb\7t\2\2\u00eb\u00ec\7k\2\2\u00ec" +
					"\u00ed\7p\2\2\u00ed\u00ee\7v\2\2\u00eeH\3\2\2\2\u00ef\u00f0\7j\2\2\u00f0" +
					"\u00f1\7c\2\2\u00f1\u00f2\7n\2\2\u00f2\u00f3\7v\2\2\u00f3J\3\2\2\2\u00f4" +
					"\u00f5\7<\2\2\u00f5L\3\2\2\2\u00f6\u00f7\7a\2\2\u00f7\u00f9\7a\2\2\u00f8" +
					"\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fe\t\2" +
					"\2\2\u00fb\u00fd\t\3\2\2\u00fc\u00fb\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe" +
					"\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ffN\3\2\2\2\u0100\u00fe\3\2\2\2" +
					"\u0101\u010d\7\62\2\2\u0102\u0104\7/\2\2\u0103\u0102\3\2\2\2\u0103\u0104" +
					"\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0109\4\63;\2\u0106\u0108\4\62;\2\u0107" +
					"\u0106\3\2\2\2\u0108\u010b\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u010a\3\2" +
					"\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010c\u0101\3\2\2\2\u010c" +
					"\u0103\3\2\2\2\u010dP\3\2\2\2\u010e\u0116\5S*\2\u010f\u0116\5U+\2\u0110" +
					"\u0116\5W,\2\u0111\u0116\5Y-\2\u0112\u0116\5[.\2\u0113\u0116\5]/\2\u0114" +
					"\u0116\5_\60\2\u0115\u010e\3\2\2\2\u0115\u010f\3\2\2\2\u0115\u0110\3\2" +
					"\2\2\u0115\u0111\3\2\2\2\u0115\u0112\3\2\2\2\u0115\u0113\3\2\2\2\u0115" +
					"\u0114\3\2\2\2\u0116R\3\2\2\2\u0117\u0118\7&\2\2\u0118\u0119\7c\2\2\u0119" +
					"\u011a\7\62\2\2\u011aT\3\2\2\2\u011b\u011c\7&\2\2\u011c\u011d\7v\2\2\u011d" +
					"\u011e\7\62\2\2\u011eV\3\2\2\2\u011f\u0120\7&\2\2\u0120\u0121\7u\2\2\u0121" +
					"\u0122\7r\2\2\u0122X\3\2\2\2\u0123\u0124\7&\2\2\u0124\u0125\7t\2\2\u0125" +
					"\u0126\7c\2\2\u0126Z\3\2\2\2\u0127\u0128\7&\2\2\u0128\u0129\7h\2\2\u0129" +
					"\u012a\7r\2\2\u012a\\\3\2\2\2\u012b\u012c\7&\2\2\u012c\u012d\7c\2\2\u012d" +
					"\u012e\7n\2\2\u012e^\3\2\2\2\u012f\u0130\7&\2\2\u0130\u0131\7j\2\2\u0131" +
					"\u0132\7r\2\2\u0132`\3\2\2\2\u0133\u0135\t\4\2\2\u0134\u0133\3\2\2\2\u0135" +
					"\u0136\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138\3\2" +
					"\2\2\u0138\u0139\b\61\2\2\u0139b\3\2\2\2\u013a\u013b\13\2\2\2\u013b\u013c" +
					"\b\62\3\2\u013c\u013d\3\2\2\2\u013d\u013e\b\62\2\2\u013ed\3\2\2\2\n\2" +
					"\u00f8\u00fe\u0103\u0109\u010c\u0115\u0136\4\2\3\2\3\62\2";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}