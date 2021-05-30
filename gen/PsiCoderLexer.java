// Generated from D:/JavaPrograms/PsiCoderANTLR/grammar\PsiCoder.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PsiCoderLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, SINGLE_COMMENT=22, MULT_COMMENT=23, 
		SP_SYMBOL=24, THEN=25, CASE=26, DEFAULT=27, ROMPER=28, AD_OP=29, MINUS=30, 
		MULT_OP=31, ASSIGN=32, COMP_OP=33, EQUAL_OP=34, AND=35, OR=36, NEG=37, 
		COLON=38, SEMI_COLON=39, COMMA=40, DOT=41, LEFT_PAR=42, RIGHT_PAR=43, 
		DATA_TYPE=44, BOOLEAN=45, ID=46, INT=47, REAL=48, CHAR=49, STRING=50;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "SINGLE_COMMENT", "MULT_COMMENT", 
			"SP_SYMBOL", "THEN", "CASE", "DEFAULT", "ROMPER", "AD_OP", "MINUS", "MULT_OP", 
			"ASSIGN", "COMP_OP", "EQUAL_OP", "AND", "OR", "NEG", "COLON", "SEMI_COLON", 
			"COMMA", "DOT", "LEFT_PAR", "RIGHT_PAR", "DATA_TYPE", "BOOLEAN", "ID", 
			"INT", "REAL", "CHAR", "STRING"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'funcion_principal'", "'fin_principal'", "'estructura'", "'fin_estructura'", 
			"'funcion'", "'hacer'", "'fin_funcion'", "'retornar'", "'leer'", "'imprimir'", 
			"'si'", "'entonces'", "'fin_si'", "'mientras'", "'fin_mientras'", "'para'", 
			"'fin_para'", "'seleccionar'", "'entre'", "'fin_seleccionar'", "'default'", 
			null, null, null, "'si_no'", "'caso'", "'defecto'", "'romper'", null, 
			"'-'", null, "'='", null, null, "'&&'", "'||'", "'!'", "':'", "';'", 
			"','", "'.'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "SINGLE_COMMENT", 
			"MULT_COMMENT", "SP_SYMBOL", "THEN", "CASE", "DEFAULT", "ROMPER", "AD_OP", 
			"MINUS", "MULT_OP", "ASSIGN", "COMP_OP", "EQUAL_OP", "AND", "OR", "NEG", 
			"COLON", "SEMI_COLON", "COMMA", "DOT", "LEFT_PAR", "RIGHT_PAR", "DATA_TYPE", 
			"BOOLEAN", "ID", "INT", "REAL", "CHAR", "STRING"
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


	public PsiCoderLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PsiCoder.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\64\u01f1\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27"+
		"\3\27\3\27\3\27\7\27\u0138\n\27\f\27\16\27\u013b\13\27\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\7\30\u0143\n\30\f\30\16\30\u0146\13\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\31\6\31\u014e\n\31\r\31\16\31\u014f\3\31\3\31\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3"+
		"\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\5\36\u0170"+
		"\n\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\5\"\u017d\n\"\3#\3#\3"+
		"#\3#\5#\u0183\n#\3$\3$\3$\3%\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+"+
		"\3+\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-"+
		"\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u01b9\n-\3.\3.\3.\3.\3.\3.\3."+
		"\3.\3.\3.\3.\3.\3.\3.\5.\u01c9\n.\3/\3/\7/\u01cd\n/\f/\16/\u01d0\13/\3"+
		"\60\6\60\u01d3\n\60\r\60\16\60\u01d4\3\61\6\61\u01d8\n\61\r\61\16\61\u01d9"+
		"\3\61\3\61\3\61\6\61\u01df\n\61\r\61\16\61\u01e0\5\61\u01e3\n\61\3\62"+
		"\3\62\3\62\3\62\3\63\3\63\7\63\u01eb\n\63\f\63\16\63\u01ee\13\63\3\63"+
		"\3\63\3\u0144\2\64\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64\3\2\f\4\2\f\f\17\17\5\2\13\f\17\17\"\"\5\2\'\',,\61\61\4\2>>@@\4"+
		"\2C\\c|\6\2\62;C\\aac|\3\2\62;\3\2\60\60\4\2\u00b3\u00b3\u00c5\u00c5\5"+
		"\2\13\f\17\17$$\2\u0202\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3"+
		"\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\3g\3\2\2\2\5y\3\2\2"+
		"\2\7\u0087\3\2\2\2\t\u0092\3\2\2\2\13\u00a1\3\2\2\2\r\u00a9\3\2\2\2\17"+
		"\u00af\3\2\2\2\21\u00bb\3\2\2\2\23\u00c4\3\2\2\2\25\u00c9\3\2\2\2\27\u00d2"+
		"\3\2\2\2\31\u00d5\3\2\2\2\33\u00de\3\2\2\2\35\u00e5\3\2\2\2\37\u00ee\3"+
		"\2\2\2!\u00fb\3\2\2\2#\u0100\3\2\2\2%\u0109\3\2\2\2\'\u0115\3\2\2\2)\u011b"+
		"\3\2\2\2+\u012b\3\2\2\2-\u0133\3\2\2\2/\u013e\3\2\2\2\61\u014d\3\2\2\2"+
		"\63\u0153\3\2\2\2\65\u0159\3\2\2\2\67\u015e\3\2\2\29\u0166\3\2\2\2;\u016f"+
		"\3\2\2\2=\u0171\3\2\2\2?\u0173\3\2\2\2A\u0175\3\2\2\2C\u017c\3\2\2\2E"+
		"\u0182\3\2\2\2G\u0184\3\2\2\2I\u0187\3\2\2\2K\u018a\3\2\2\2M\u018c\3\2"+
		"\2\2O\u018e\3\2\2\2Q\u0190\3\2\2\2S\u0192\3\2\2\2U\u0194\3\2\2\2W\u0196"+
		"\3\2\2\2Y\u01b8\3\2\2\2[\u01c8\3\2\2\2]\u01ca\3\2\2\2_\u01d2\3\2\2\2a"+
		"\u01d7\3\2\2\2c\u01e4\3\2\2\2e\u01e8\3\2\2\2gh\7h\2\2hi\7w\2\2ij\7p\2"+
		"\2jk\7e\2\2kl\7k\2\2lm\7q\2\2mn\7p\2\2no\7a\2\2op\7r\2\2pq\7t\2\2qr\7"+
		"k\2\2rs\7p\2\2st\7e\2\2tu\7k\2\2uv\7r\2\2vw\7c\2\2wx\7n\2\2x\4\3\2\2\2"+
		"yz\7h\2\2z{\7k\2\2{|\7p\2\2|}\7a\2\2}~\7r\2\2~\177\7t\2\2\177\u0080\7"+
		"k\2\2\u0080\u0081\7p\2\2\u0081\u0082\7e\2\2\u0082\u0083\7k\2\2\u0083\u0084"+
		"\7r\2\2\u0084\u0085\7c\2\2\u0085\u0086\7n\2\2\u0086\6\3\2\2\2\u0087\u0088"+
		"\7g\2\2\u0088\u0089\7u\2\2\u0089\u008a\7v\2\2\u008a\u008b\7t\2\2\u008b"+
		"\u008c\7w\2\2\u008c\u008d\7e\2\2\u008d\u008e\7v\2\2\u008e\u008f\7w\2\2"+
		"\u008f\u0090\7t\2\2\u0090\u0091\7c\2\2\u0091\b\3\2\2\2\u0092\u0093\7h"+
		"\2\2\u0093\u0094\7k\2\2\u0094\u0095\7p\2\2\u0095\u0096\7a\2\2\u0096\u0097"+
		"\7g\2\2\u0097\u0098\7u\2\2\u0098\u0099\7v\2\2\u0099\u009a\7t\2\2\u009a"+
		"\u009b\7w\2\2\u009b\u009c\7e\2\2\u009c\u009d\7v\2\2\u009d\u009e\7w\2\2"+
		"\u009e\u009f\7t\2\2\u009f\u00a0\7c\2\2\u00a0\n\3\2\2\2\u00a1\u00a2\7h"+
		"\2\2\u00a2\u00a3\7w\2\2\u00a3\u00a4\7p\2\2\u00a4\u00a5\7e\2\2\u00a5\u00a6"+
		"\7k\2\2\u00a6\u00a7\7q\2\2\u00a7\u00a8\7p\2\2\u00a8\f\3\2\2\2\u00a9\u00aa"+
		"\7j\2\2\u00aa\u00ab\7c\2\2\u00ab\u00ac\7e\2\2\u00ac\u00ad\7g\2\2\u00ad"+
		"\u00ae\7t\2\2\u00ae\16\3\2\2\2\u00af\u00b0\7h\2\2\u00b0\u00b1\7k\2\2\u00b1"+
		"\u00b2\7p\2\2\u00b2\u00b3\7a\2\2\u00b3\u00b4\7h\2\2\u00b4\u00b5\7w\2\2"+
		"\u00b5\u00b6\7p\2\2\u00b6\u00b7\7e\2\2\u00b7\u00b8\7k\2\2\u00b8\u00b9"+
		"\7q\2\2\u00b9\u00ba\7p\2\2\u00ba\20\3\2\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd"+
		"\7g\2\2\u00bd\u00be\7v\2\2\u00be\u00bf\7q\2\2\u00bf\u00c0\7t\2\2\u00c0"+
		"\u00c1\7p\2\2\u00c1\u00c2\7c\2\2\u00c2\u00c3\7t\2\2\u00c3\22\3\2\2\2\u00c4"+
		"\u00c5\7n\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7t\2\2"+
		"\u00c8\24\3\2\2\2\u00c9\u00ca\7k\2\2\u00ca\u00cb\7o\2\2\u00cb\u00cc\7"+
		"r\2\2\u00cc\u00cd\7t\2\2\u00cd\u00ce\7k\2\2\u00ce\u00cf\7o\2\2\u00cf\u00d0"+
		"\7k\2\2\u00d0\u00d1\7t\2\2\u00d1\26\3\2\2\2\u00d2\u00d3\7u\2\2\u00d3\u00d4"+
		"\7k\2\2\u00d4\30\3\2\2\2\u00d5\u00d6\7g\2\2\u00d6\u00d7\7p\2\2\u00d7\u00d8"+
		"\7v\2\2\u00d8\u00d9\7q\2\2\u00d9\u00da\7p\2\2\u00da\u00db\7e\2\2\u00db"+
		"\u00dc\7g\2\2\u00dc\u00dd\7u\2\2\u00dd\32\3\2\2\2\u00de\u00df\7h\2\2\u00df"+
		"\u00e0\7k\2\2\u00e0\u00e1\7p\2\2\u00e1\u00e2\7a\2\2\u00e2\u00e3\7u\2\2"+
		"\u00e3\u00e4\7k\2\2\u00e4\34\3\2\2\2\u00e5\u00e6\7o\2\2\u00e6\u00e7\7"+
		"k\2\2\u00e7\u00e8\7g\2\2\u00e8\u00e9\7p\2\2\u00e9\u00ea\7v\2\2\u00ea\u00eb"+
		"\7t\2\2\u00eb\u00ec\7c\2\2\u00ec\u00ed\7u\2\2\u00ed\36\3\2\2\2\u00ee\u00ef"+
		"\7h\2\2\u00ef\u00f0\7k\2\2\u00f0\u00f1\7p\2\2\u00f1\u00f2\7a\2\2\u00f2"+
		"\u00f3\7o\2\2\u00f3\u00f4\7k\2\2\u00f4\u00f5\7g\2\2\u00f5\u00f6\7p\2\2"+
		"\u00f6\u00f7\7v\2\2\u00f7\u00f8\7t\2\2\u00f8\u00f9\7c\2\2\u00f9\u00fa"+
		"\7u\2\2\u00fa \3\2\2\2\u00fb\u00fc\7r\2\2\u00fc\u00fd\7c\2\2\u00fd\u00fe"+
		"\7t\2\2\u00fe\u00ff\7c\2\2\u00ff\"\3\2\2\2\u0100\u0101\7h\2\2\u0101\u0102"+
		"\7k\2\2\u0102\u0103\7p\2\2\u0103\u0104\7a\2\2\u0104\u0105\7r\2\2\u0105"+
		"\u0106\7c\2\2\u0106\u0107\7t\2\2\u0107\u0108\7c\2\2\u0108$\3\2\2\2\u0109"+
		"\u010a\7u\2\2\u010a\u010b\7g\2\2\u010b\u010c\7n\2\2\u010c\u010d\7g\2\2"+
		"\u010d\u010e\7e\2\2\u010e\u010f\7e\2\2\u010f\u0110\7k\2\2\u0110\u0111"+
		"\7q\2\2\u0111\u0112\7p\2\2\u0112\u0113\7c\2\2\u0113\u0114\7t\2\2\u0114"+
		"&\3\2\2\2\u0115\u0116\7g\2\2\u0116\u0117\7p\2\2\u0117\u0118\7v\2\2\u0118"+
		"\u0119\7t\2\2\u0119\u011a\7g\2\2\u011a(\3\2\2\2\u011b\u011c\7h\2\2\u011c"+
		"\u011d\7k\2\2\u011d\u011e\7p\2\2\u011e\u011f\7a\2\2\u011f\u0120\7u\2\2"+
		"\u0120\u0121\7g\2\2\u0121\u0122\7n\2\2\u0122\u0123\7g\2\2\u0123\u0124"+
		"\7e\2\2\u0124\u0125\7e\2\2\u0125\u0126\7k\2\2\u0126\u0127\7q\2\2\u0127"+
		"\u0128\7p\2\2\u0128\u0129\7c\2\2\u0129\u012a\7t\2\2\u012a*\3\2\2\2\u012b"+
		"\u012c\7f\2\2\u012c\u012d\7g\2\2\u012d\u012e\7h\2\2\u012e\u012f\7c\2\2"+
		"\u012f\u0130\7w\2\2\u0130\u0131\7n\2\2\u0131\u0132\7v\2\2\u0132,\3\2\2"+
		"\2\u0133\u0134\7\61\2\2\u0134\u0135\7\61\2\2\u0135\u0139\3\2\2\2\u0136"+
		"\u0138\n\2\2\2\u0137\u0136\3\2\2\2\u0138\u013b\3\2\2\2\u0139\u0137\3\2"+
		"\2\2\u0139\u013a\3\2\2\2\u013a\u013c\3\2\2\2\u013b\u0139\3\2\2\2\u013c"+
		"\u013d\b\27\2\2\u013d.\3\2\2\2\u013e\u013f\7\61\2\2\u013f\u0140\7,\2\2"+
		"\u0140\u0144\3\2\2\2\u0141\u0143\13\2\2\2\u0142\u0141\3\2\2\2\u0143\u0146"+
		"\3\2\2\2\u0144\u0145\3\2\2\2\u0144\u0142\3\2\2\2\u0145\u0147\3\2\2\2\u0146"+
		"\u0144\3\2\2\2\u0147\u0148\7,\2\2\u0148\u0149\7\61\2\2\u0149\u014a\3\2"+
		"\2\2\u014a\u014b\b\30\2\2\u014b\60\3\2\2\2\u014c\u014e\t\3\2\2\u014d\u014c"+
		"\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150"+
		"\u0151\3\2\2\2\u0151\u0152\b\31\2\2\u0152\62\3\2\2\2\u0153\u0154\7u\2"+
		"\2\u0154\u0155\7k\2\2\u0155\u0156\7a\2\2\u0156\u0157\7p\2\2\u0157\u0158"+
		"\7q\2\2\u0158\64\3\2\2\2\u0159\u015a\7e\2\2\u015a\u015b\7c\2\2\u015b\u015c"+
		"\7u\2\2\u015c\u015d\7q\2\2\u015d\66\3\2\2\2\u015e\u015f\7f\2\2\u015f\u0160"+
		"\7g\2\2\u0160\u0161\7h\2\2\u0161\u0162\7g\2\2\u0162\u0163\7e\2\2\u0163"+
		"\u0164\7v\2\2\u0164\u0165\7q\2\2\u01658\3\2\2\2\u0166\u0167\7t\2\2\u0167"+
		"\u0168\7q\2\2\u0168\u0169\7o\2\2\u0169\u016a\7r\2\2\u016a\u016b\7g\2\2"+
		"\u016b\u016c\7t\2\2\u016c:\3\2\2\2\u016d\u0170\7-\2\2\u016e\u0170\5=\37"+
		"\2\u016f\u016d\3\2\2\2\u016f\u016e\3\2\2\2\u0170<\3\2\2\2\u0171\u0172"+
		"\7/\2\2\u0172>\3\2\2\2\u0173\u0174\t\4\2\2\u0174@\3\2\2\2\u0175\u0176"+
		"\7?\2\2\u0176B\3\2\2\2\u0177\u017d\t\5\2\2\u0178\u0179\7>\2\2\u0179\u017d"+
		"\7?\2\2\u017a\u017b\7@\2\2\u017b\u017d\7?\2\2\u017c\u0177\3\2\2\2\u017c"+
		"\u0178\3\2\2\2\u017c\u017a\3\2\2\2\u017dD\3\2\2\2\u017e\u017f\7?\2\2\u017f"+
		"\u0183\7?\2\2\u0180\u0181\7#\2\2\u0181\u0183\7?\2\2\u0182\u017e\3\2\2"+
		"\2\u0182\u0180\3\2\2\2\u0183F\3\2\2\2\u0184\u0185\7(\2\2\u0185\u0186\7"+
		"(\2\2\u0186H\3\2\2\2\u0187\u0188\7~\2\2\u0188\u0189\7~\2\2\u0189J\3\2"+
		"\2\2\u018a\u018b\7#\2\2\u018bL\3\2\2\2\u018c\u018d\7<\2\2\u018dN\3\2\2"+
		"\2\u018e\u018f\7=\2\2\u018fP\3\2\2\2\u0190\u0191\7.\2\2\u0191R\3\2\2\2"+
		"\u0192\u0193\7\60\2\2\u0193T\3\2\2\2\u0194\u0195\7*\2\2\u0195V\3\2\2\2"+
		"\u0196\u0197\7+\2\2\u0197X\3\2\2\2\u0198\u0199\7d\2\2\u0199\u019a\7q\2"+
		"\2\u019a\u019b\7q\2\2\u019b\u019c\7n\2\2\u019c\u019d\7g\2\2\u019d\u019e"+
		"\7c\2\2\u019e\u019f\7p\2\2\u019f\u01b9\7q\2\2\u01a0\u01a1\7e\2\2\u01a1"+
		"\u01a2\7c\2\2\u01a2\u01a3\7t\2\2\u01a3\u01a4\7c\2\2\u01a4\u01a5\7e\2\2"+
		"\u01a5\u01a6\7v\2\2\u01a6\u01a7\7g\2\2\u01a7\u01b9\7t\2\2\u01a8\u01a9"+
		"\7g\2\2\u01a9\u01aa\7p\2\2\u01aa\u01ab\7v\2\2\u01ab\u01ac\7g\2\2\u01ac"+
		"\u01ad\7t\2\2\u01ad\u01b9\7q\2\2\u01ae\u01af\7t\2\2\u01af\u01b0\7g\2\2"+
		"\u01b0\u01b1\7c\2\2\u01b1\u01b9\7n\2\2\u01b2\u01b3\7e\2\2\u01b3\u01b4"+
		"\7c\2\2\u01b4\u01b5\7f\2\2\u01b5\u01b6\7g\2\2\u01b6\u01b7\7p\2\2\u01b7"+
		"\u01b9\7c\2\2\u01b8\u0198\3\2\2\2\u01b8\u01a0\3\2\2\2\u01b8\u01a8\3\2"+
		"\2\2\u01b8\u01ae\3\2\2\2\u01b8\u01b2\3\2\2\2\u01b9Z\3\2\2\2\u01ba\u01bb"+
		"\7x\2\2\u01bb\u01bc\7g\2\2\u01bc\u01bd\7t\2\2\u01bd\u01be\7f\2\2\u01be"+
		"\u01bf\7c\2\2\u01bf\u01c0\7f\2\2\u01c0\u01c1\7g\2\2\u01c1\u01c2\7t\2\2"+
		"\u01c2\u01c9\7q\2\2\u01c3\u01c4\7h\2\2\u01c4\u01c5\7c\2\2\u01c5\u01c6"+
		"\7n\2\2\u01c6\u01c7\7u\2\2\u01c7\u01c9\7q\2\2\u01c8\u01ba\3\2\2\2\u01c8"+
		"\u01c3\3\2\2\2\u01c9\\\3\2\2\2\u01ca\u01ce\t\6\2\2\u01cb\u01cd\t\7\2\2"+
		"\u01cc\u01cb\3\2\2\2\u01cd\u01d0\3\2\2\2\u01ce\u01cc\3\2\2\2\u01ce\u01cf"+
		"\3\2\2\2\u01cf^\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d1\u01d3\t\b\2\2\u01d2"+
		"\u01d1\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4\u01d2\3\2\2\2\u01d4\u01d5\3\2"+
		"\2\2\u01d5`\3\2\2\2\u01d6\u01d8\t\b\2\2\u01d7\u01d6\3\2\2\2\u01d8\u01d9"+
		"\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da\3\2\2\2\u01da\u01e2\3\2\2\2\u01db"+
		"\u01e3\3\2\2\2\u01dc\u01de\t\t\2\2\u01dd\u01df\t\b\2\2\u01de\u01dd\3\2"+
		"\2\2\u01df\u01e0\3\2\2\2\u01e0\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1"+
		"\u01e3\3\2\2\2\u01e2\u01db\3\2\2\2\u01e2\u01dc\3\2\2\2\u01e3b\3\2\2\2"+
		"\u01e4\u01e5\7)\2\2\u01e5\u01e6\n\n\2\2\u01e6\u01e7\7)\2\2\u01e7d\3\2"+
		"\2\2\u01e8\u01ec\7$\2\2\u01e9\u01eb\n\13\2\2\u01ea\u01e9\3\2\2\2\u01eb"+
		"\u01ee\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01ef\3\2"+
		"\2\2\u01ee\u01ec\3\2\2\2\u01ef\u01f0\7$\2\2\u01f0f\3\2\2\2\21\2\u0139"+
		"\u0144\u014f\u016f\u017c\u0182\u01b8\u01c8\u01ce\u01d4\u01d9\u01e0\u01e2"+
		"\u01ec\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}