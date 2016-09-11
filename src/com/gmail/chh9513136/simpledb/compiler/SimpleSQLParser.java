// Generated from D:\DB\Recognizer\SimpleSQL.g4 by ANTLR 4.x
package com.gmail.chh9513136.simpledb.compiler;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class SimpleSQLParser extends Parser {
	public static final int
		INT=1, VARCHAR=2, SELECT=3, FROM=4, AS=5, INNER=6, JOIN=7, WHERE=8, CREATE=9, 
		TABLE=10, PRIMARY=11, KEY=12, HASH=13, BTREE=14, INSERT=15, INTO=16, VALUES=17, 
		COUNT=18, SUM=19, IS=20, NOT=21, OR=22, AND=23, NULL=24, SEMICOL=25, COLON=26, 
		DOT=27, COMMA=28, R_PAREN=29, L_PAREN=30, PLUS=31, MINUS=32, ASTER=33, 
		DIV=34, MOD=35, NEGATION=36, B_OR=37, B_AND=38, POWER_OP=39, GT=40, LT=41, 
		GE=42, LE=43, EQ=44, NEQ=45, L_SHIFT=46, R_SHIFT=47, CONCAT=48, INT_NUM=49, 
		HEX_NUM=50, BIT_NUM=51, REAL_NUM=52, TEXT_STRING=53, ID=54, WHITE_SPACE=55, 
		SL_COMMENT=56, ML_COMMENT=57;
	public static final String[] tokenNames = {
		"<INVALID>", "INT", "VARCHAR", "SELECT", "FROM", "AS", "INNER", "JOIN", 
		"WHERE", "CREATE", "TABLE", "PRIMARY", "KEY", "HASH", "BTREE", "INSERT", 
		"INTO", "VALUES", "COUNT", "SUM", "IS", "NOT", "OR", "AND", "NULL", "';'", 
		"':'", "'.'", "','", "')'", "'('", "'+'", "'-'", "'*'", "'/'", "'%'", 
		"'~'", "'|'", "'&'", "'^'", "'>'", "'<'", "'>='", "'<='", "EQ", "NEQ", 
		"'<<'", "'>>'", "'||'", "INT_NUM", "HEX_NUM", "BIT_NUM", "REAL_NUM", "TEXT_STRING", 
		"ID", "WHITE_SPACE", "SL_COMMENT", "ML_COMMENT"
	};
	public static final int
		RULE_compare = 0, RULE_numberLiteral = 1, RULE_hexLiteral = 2, RULE_bitLiteral = 3, 
		RULE_stringLiteral = 4, RULE_literalValue = 5, RULE_alias = 6, RULE_tableName = 7, 
		RULE_columnName = 8, RULE_typeLength = 9, RULE_indexType = 10, RULE_expression = 11, 
		RULE_andCondition = 12, RULE_condition = 13, RULE_condRHS = 14, RULE_term = 15, 
		RULE_statementList = 16, RULE_statement = 17, RULE_dataManipulateStmt = 18, 
		RULE_dataDefinitionStmt = 19, RULE_createTableStmt = 20, RULE_columnDefinition = 21, 
		RULE_datatype = 22, RULE_insertIntoStmt = 23, RULE_columnList = 24, RULE_valueList = 25, 
		RULE_selectStmt = 26, RULE_selectList = 27, RULE_columnSpec = 28, RULE_tableSpecList = 29, 
		RULE_tableSpec = 30, RULE_aggregate = 31;
	public static final String[] ruleNames = {
		"compare", "numberLiteral", "hexLiteral", "bitLiteral", "stringLiteral", 
		"literalValue", "alias", "tableName", "columnName", "typeLength", "indexType", 
		"expression", "andCondition", "condition", "condRHS", "term", "statementList", 
		"statement", "dataManipulateStmt", "dataDefinitionStmt", "createTableStmt", 
		"columnDefinition", "datatype", "insertIntoStmt", "columnList", "valueList", 
		"selectStmt", "selectList", "columnSpec", "tableSpecList", "tableSpec", 
		"aggregate"
	};

	@Override
	public String getGrammarFileName() { return "SimpleSQL.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	public SimpleSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN);
	}
	public static class CompareContext extends ParserRuleContext {
		public Token op;
		public TerminalNode NEQ() { return getToken(SimpleSQLParser.NEQ, 0); }
		public TerminalNode GE() { return getToken(SimpleSQLParser.GE, 0); }
		public TerminalNode LT() { return getToken(SimpleSQLParser.LT, 0); }
		public TerminalNode GT() { return getToken(SimpleSQLParser.GT, 0); }
		public TerminalNode LE() { return getToken(SimpleSQLParser.LE, 0); }
		public TerminalNode EQ() { return getToken(SimpleSQLParser.EQ, 0); }
		public CompareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitCompare(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final CompareContext compare() throws RecognitionException {
		CompareContext _localctx = new CompareContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_localctx.op = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << LT) | (1L << GE) | (1L << LE) | (1L << EQ) | (1L << NEQ))) != 0)) ) {
				_localctx.op = _errHandler.recoverInline(this);
			}
			consume();
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

	public static class NumberLiteralContext extends ParserRuleContext {
		public Token sign;
		public TerminalNode INT_NUM() { return getToken(SimpleSQLParser.INT_NUM, 0); }
		public TerminalNode PLUS() { return getToken(SimpleSQLParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(SimpleSQLParser.MINUS, 0); }
		public NumberLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberLiteral; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitNumberLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final NumberLiteralContext numberLiteral() throws RecognitionException {
		NumberLiteralContext _localctx = new NumberLiteralContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_numberLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(66);
				_localctx.sign = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					_localctx.sign = _errHandler.recoverInline(this);
				}
				consume();
				}
			}

			{
			setState(69); match(INT_NUM);
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

	public static class HexLiteralContext extends ParserRuleContext {
		public TerminalNode HEX_NUM() { return getToken(SimpleSQLParser.HEX_NUM, 0); }
		public HexLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hexLiteral; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitHexLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final HexLiteralContext hexLiteral() throws RecognitionException {
		HexLiteralContext _localctx = new HexLiteralContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_hexLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71); match(HEX_NUM);
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

	public static class BitLiteralContext extends ParserRuleContext {
		public TerminalNode BIT_NUM() { return getToken(SimpleSQLParser.BIT_NUM, 0); }
		public BitLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitLiteral; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitBitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final BitLiteralContext bitLiteral() throws RecognitionException {
		BitLiteralContext _localctx = new BitLiteralContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_bitLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); match(BIT_NUM);
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

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode TEXT_STRING() { return getToken(SimpleSQLParser.TEXT_STRING, 0); }
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); match(TEXT_STRING);
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

	public static class LiteralValueContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(SimpleSQLParser.NULL, 0); }
		public NumberLiteralContext numberLiteral() {
			return getRuleContext(NumberLiteralContext.class,0);
		}
		public BitLiteralContext bitLiteral() {
			return getRuleContext(BitLiteralContext.class,0);
		}
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public HexLiteralContext hexLiteral() {
			return getRuleContext(HexLiteralContext.class,0);
		}
		public LiteralValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalValue; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitLiteralValue(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final LiteralValueContext literalValue() throws RecognitionException {
		LiteralValueContext _localctx = new LiteralValueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_literalValue);
		try {
			setState(82);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case INT_NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(77); numberLiteral();
				}
				break;
			case HEX_NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(78); hexLiteral();
				}
				break;
			case BIT_NUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(79); bitLiteral();
				}
				break;
			case TEXT_STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(80); stringLiteral();
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 5);
				{
				setState(81); match(NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class AliasContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SimpleSQLParser.ID, 0); }
		public TerminalNode AS() { return getToken(SimpleSQLParser.AS, 0); }
		public AliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alias; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitAlias(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final AliasContext alias() throws RecognitionException {
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_alias);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(84); match(AS);
				}
			}

			setState(87); match(ID);
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

	public static class TableNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SimpleSQLParser.ID, 0); }
		public TerminalNode TEXT_STRING() { return getToken(SimpleSQLParser.TEXT_STRING, 0); }
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tableName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_la = _input.LA(1);
			if ( !(_la==TEXT_STRING || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class ColumnNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SimpleSQLParser.ID, 0); }
		public TerminalNode TEXT_STRING() { return getToken(SimpleSQLParser.TEXT_STRING, 0); }
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_columnName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			_la = _input.LA(1);
			if ( !(_la==TEXT_STRING || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class TypeLengthContext extends ParserRuleContext {
		public TerminalNode INT_NUM() { return getToken(SimpleSQLParser.INT_NUM, 0); }
		public TypeLengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeLength; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitTypeLength(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final TypeLengthContext typeLength() throws RecognitionException {
		TypeLengthContext _localctx = new TypeLengthContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_typeLength);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93); match(INT_NUM);
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

	public static class IndexTypeContext extends ParserRuleContext {
		public TerminalNode BTREE() { return getToken(SimpleSQLParser.BTREE, 0); }
		public TerminalNode HASH() { return getToken(SimpleSQLParser.HASH, 0); }
		public IndexTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexType; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitIndexType(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final IndexTypeContext indexType() throws RecognitionException {
		IndexTypeContext _localctx = new IndexTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_indexType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			_la = _input.LA(1);
			if ( !(_la==HASH || _la==BTREE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class ExpressionContext extends ParserRuleContext {
		public List<? extends AndConditionContext> andCondition() {
			return getRuleContexts(AndConditionContext.class);
		}
		public AndConditionContext andCondition(int i) {
			return getRuleContext(AndConditionContext.class,i);
		}
		public List<? extends TerminalNode> OR() { return getTokens(SimpleSQLParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(SimpleSQLParser.OR, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); andCondition();
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(98); match(OR);
				setState(99); andCondition();
				}
				}
				setState(104);
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

	public static class AndConditionContext extends ParserRuleContext {
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode AND(int i) {
			return getToken(SimpleSQLParser.AND, i);
		}
		public List<? extends TerminalNode> AND() { return getTokens(SimpleSQLParser.AND); }
		public List<? extends ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public AndConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andCondition; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitAndCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final AndConditionContext andCondition() throws RecognitionException {
		AndConditionContext _localctx = new AndConditionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_andCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105); condition();
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(106); match(AND);
				setState(107); condition();
				}
				}
				setState(112);
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

	public static class ConditionContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(SimpleSQLParser.NOT, 0); }
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public CondRHSContext condRHS() {
			return getRuleContext(CondRHSContext.class,0);
		}
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_condition);
		try {
			setState(122);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(113); term();
				setState(114); condRHS();
				}
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(116); match(NOT);
				setState(117); condition();
				}
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(118); match(L_PAREN);
				setState(119); expression();
				setState(120); match(R_PAREN);
				}
				}
				break;
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

	public static class CondRHSContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(SimpleSQLParser.NOT, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode IS() { return getToken(SimpleSQLParser.IS, 0); }
		public TerminalNode NULL() { return getToken(SimpleSQLParser.NULL, 0); }
		public CompareContext compare() {
			return getRuleContext(CompareContext.class,0);
		}
		public CondRHSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condRHS; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitCondRHS(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final CondRHSContext condRHS() throws RecognitionException {
		CondRHSContext _localctx = new CondRHSContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_condRHS);
		int _la;
		try {
			setState(132);
			switch (_input.LA(1)) {
			case GT:
			case LT:
			case GE:
			case LE:
			case EQ:
			case NEQ:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(124); compare();
				setState(125); term();
				}
				}
				break;
			case IS:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(127); match(IS);
				setState(129);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(128); match(NOT);
					}
				}

				setState(131); match(NULL);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class TermContext extends ParserRuleContext {
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public ColumnSpecContext columnSpec() {
			return getRuleContext(ColumnSpecContext.class,0);
		}
		public LiteralValueContext literalValue() {
			return getRuleContext(LiteralValueContext.class,0);
		}
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_term);
		try {
			setState(140);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(134); literalValue();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(135); columnSpec();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(136); match(L_PAREN);
				setState(137); term();
				setState(138); match(R_PAREN);
				}
				}
				break;
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

	public static class StatementListContext extends ParserRuleContext {
		public TerminalNode SEMICOL(int i) {
			return getToken(SimpleSQLParser.SEMICOL, i);
		}
		public TerminalNode EOF() { return getToken(SimpleSQLParser.EOF, 0); }
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<? extends TerminalNode> SEMICOL() { return getTokens(SimpleSQLParser.SEMICOL); }
		public List<? extends StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementList; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitStatementList(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final StatementListContext statementList() throws RecognitionException {
		StatementListContext _localctx = new StatementListContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SELECT) | (1L << CREATE) | (1L << INSERT))) != 0)) {
				{
				setState(142); statement();
				}
			}

			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMICOL) {
				{
				{
				setState(145); match(SEMICOL);
				setState(147);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SELECT) | (1L << CREATE) | (1L << INSERT))) != 0)) {
					{
					setState(146); statement();
					}
				}

				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(154); match(EOF);
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

	public static class StatementContext extends ParserRuleContext {
		public DataDefinitionStmtContext dataDefinitionStmt() {
			return getRuleContext(DataDefinitionStmtContext.class,0);
		}
		public DataManipulateStmtContext dataManipulateStmt() {
			return getRuleContext(DataManipulateStmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			switch (_input.LA(1)) {
			case SELECT:
			case INSERT:
				{
				setState(156); dataManipulateStmt();
				}
				break;
			case CREATE:
				{
				setState(157); dataDefinitionStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class DataManipulateStmtContext extends ParserRuleContext {
		public InsertIntoStmtContext insertIntoStmt() {
			return getRuleContext(InsertIntoStmtContext.class,0);
		}
		public SelectStmtContext selectStmt() {
			return getRuleContext(SelectStmtContext.class,0);
		}
		public DataManipulateStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataManipulateStmt; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitDataManipulateStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final DataManipulateStmtContext dataManipulateStmt() throws RecognitionException {
		DataManipulateStmtContext _localctx = new DataManipulateStmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_dataManipulateStmt);
		try {
			setState(162);
			switch (_input.LA(1)) {
			case INSERT:
				enterOuterAlt(_localctx, 1);
				{
				setState(160); insertIntoStmt();
				}
				break;
			case SELECT:
				enterOuterAlt(_localctx, 2);
				{
				setState(161); selectStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class DataDefinitionStmtContext extends ParserRuleContext {
		public CreateTableStmtContext createTableStmt() {
			return getRuleContext(CreateTableStmtContext.class,0);
		}
		public DataDefinitionStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataDefinitionStmt; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitDataDefinitionStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final DataDefinitionStmtContext dataDefinitionStmt() throws RecognitionException {
		DataDefinitionStmtContext _localctx = new DataDefinitionStmtContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_dataDefinitionStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164); createTableStmt();
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

	public static class CreateTableStmtContext extends ParserRuleContext {
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public List<? extends ColumnDefinitionContext> columnDefinition() {
			return getRuleContexts(ColumnDefinitionContext.class);
		}
		public List<? extends TerminalNode> COMMA() { return getTokens(SimpleSQLParser.COMMA); }
		public TerminalNode CREATE() { return getToken(SimpleSQLParser.CREATE, 0); }
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public TerminalNode TABLE() { return getToken(SimpleSQLParser.TABLE, 0); }
		public ColumnDefinitionContext columnDefinition(int i) {
			return getRuleContext(ColumnDefinitionContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(SimpleSQLParser.COMMA, i);
		}
		public CreateTableStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTableStmt; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitCreateTableStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final CreateTableStmtContext createTableStmt() throws RecognitionException {
		CreateTableStmtContext _localctx = new CreateTableStmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_createTableStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166); match(CREATE);
			setState(167); match(TABLE);
			setState(168); tableName();
			setState(169); match(L_PAREN);
			setState(170); columnDefinition();
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(171); match(COMMA);
				setState(172); columnDefinition();
				}
				}
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(178); match(R_PAREN);
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

	public static class ColumnDefinitionContext extends ParserRuleContext {
		public TerminalNode PRIMARY() { return getToken(SimpleSQLParser.PRIMARY, 0); }
		public TerminalNode KEY() { return getToken(SimpleSQLParser.KEY, 0); }
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public DatatypeContext datatype() {
			return getRuleContext(DatatypeContext.class,0);
		}
		public IndexTypeContext indexType() {
			return getRuleContext(IndexTypeContext.class,0);
		}
		public ColumnDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinition; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitColumnDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ColumnDefinitionContext columnDefinition() throws RecognitionException {
		ColumnDefinitionContext _localctx = new ColumnDefinitionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_columnDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180); columnName();
			setState(181); datatype();
			setState(184);
			_la = _input.LA(1);
			if (_la==PRIMARY) {
				{
				setState(182); match(PRIMARY);
				setState(183); match(KEY);
				}
			}

			setState(187);
			_la = _input.LA(1);
			if (_la==HASH || _la==BTREE) {
				{
				setState(186); indexType();
				}
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

	public static class DatatypeContext extends ParserRuleContext {
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public TerminalNode INT() { return getToken(SimpleSQLParser.INT, 0); }
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public TypeLengthContext typeLength() {
			return getRuleContext(TypeLengthContext.class,0);
		}
		public TerminalNode VARCHAR() { return getToken(SimpleSQLParser.VARCHAR, 0); }
		public DatatypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datatype; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitDatatype(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final DatatypeContext datatype() throws RecognitionException {
		DatatypeContext _localctx = new DatatypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_datatype);
		try {
			setState(195);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(189); match(INT);
				}
				}
				break;
			case VARCHAR:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(190); match(VARCHAR);
				setState(191); match(L_PAREN);
				setState(192); typeLength();
				setState(193); match(R_PAREN);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class InsertIntoStmtContext extends ParserRuleContext {
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode VALUES() { return getToken(SimpleSQLParser.VALUES, 0); }
		public TerminalNode INSERT() { return getToken(SimpleSQLParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(SimpleSQLParser.INTO, 0); }
		public InsertIntoStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertIntoStmt; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitInsertIntoStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final InsertIntoStmtContext insertIntoStmt() throws RecognitionException {
		InsertIntoStmtContext _localctx = new InsertIntoStmtContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_insertIntoStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197); match(INSERT);
			setState(198); match(INTO);
			setState(199); tableName();
			setState(201);
			_la = _input.LA(1);
			if (_la==L_PAREN) {
				{
				setState(200); columnList();
				}
			}

			setState(203); match(VALUES);
			setState(204); valueList();
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

	public static class ColumnListContext extends ParserRuleContext {
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public List<? extends TerminalNode> COMMA() { return getTokens(SimpleSQLParser.COMMA); }
		public List<? extends ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(SimpleSQLParser.COMMA, i);
		}
		public ColumnListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnList; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitColumnList(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ColumnListContext columnList() throws RecognitionException {
		ColumnListContext _localctx = new ColumnListContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_columnList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206); match(L_PAREN);
			setState(207); columnName();
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(208); match(COMMA);
				setState(209); columnName();
				}
				}
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(215); match(R_PAREN);
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

	public static class ValueListContext extends ParserRuleContext {
		public LiteralValueContext literalValue(int i) {
			return getRuleContext(LiteralValueContext.class,i);
		}
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public List<? extends TerminalNode> COMMA() { return getTokens(SimpleSQLParser.COMMA); }
		public List<? extends LiteralValueContext> literalValue() {
			return getRuleContexts(LiteralValueContext.class);
		}
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(SimpleSQLParser.COMMA, i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_valueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217); match(L_PAREN);
			setState(218); literalValue();
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(219); match(COMMA);
				setState(220); literalValue();
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(226); match(R_PAREN);
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

	public static class SelectStmtContext extends ParserRuleContext {
		public TableSpecListContext tableSpecList() {
			return getRuleContext(TableSpecListContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(SimpleSQLParser.WHERE, 0); }
		public TerminalNode FROM() { return getToken(SimpleSQLParser.FROM, 0); }
		public AggregateContext aggregate() {
			return getRuleContext(AggregateContext.class,0);
		}
		public TerminalNode SELECT() { return getToken(SimpleSQLParser.SELECT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SelectListContext selectList() {
			return getRuleContext(SelectListContext.class,0);
		}
		public SelectStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectStmt; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitSelectStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final SelectStmtContext selectStmt() throws RecognitionException {
		SelectStmtContext _localctx = new SelectStmtContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_selectStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228); match(SELECT);
			setState(231);
			switch (_input.LA(1)) {
			case ASTER:
			case TEXT_STRING:
			case ID:
				{
				setState(229); selectList();
				}
				break;
			case COUNT:
			case SUM:
				{
				setState(230); aggregate();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(233); match(FROM);
			setState(234); tableSpecList();
			setState(237);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(235); match(WHERE);
				setState(236); expression();
				}
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

	public static class SelectListContext extends ParserRuleContext {
		public ColumnSpecContext columnSpec(int i) {
			return getRuleContext(ColumnSpecContext.class,i);
		}
		public List<? extends ColumnSpecContext> columnSpec() {
			return getRuleContexts(ColumnSpecContext.class);
		}
		public List<? extends TerminalNode> COMMA() { return getTokens(SimpleSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SimpleSQLParser.COMMA, i);
		}
		public SelectListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectList; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitSelectList(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final SelectListContext selectList() throws RecognitionException {
		SelectListContext _localctx = new SelectListContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_selectList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(239); columnSpec();
			setState(244);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(240); match(COMMA);
				setState(241); columnSpec();
				}
				}
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class ColumnSpecContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(SimpleSQLParser.DOT, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public TerminalNode ASTER() { return getToken(SimpleSQLParser.ASTER, 0); }
		public ColumnSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnSpec; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitColumnSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final ColumnSpecContext columnSpec() throws RecognitionException {
		ColumnSpecContext _localctx = new ColumnSpecContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_columnSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(247); tableName();
				setState(248); match(DOT);
				}
				break;
			}
			setState(254);
			switch (_input.LA(1)) {
			case TEXT_STRING:
			case ID:
				{
				setState(252); columnName();
				}
				break;
			case ASTER:
				{
				setState(253); match(ASTER);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class TableSpecListContext extends ParserRuleContext {
		public TableSpecContext tableSpec(int i) {
			return getRuleContext(TableSpecContext.class,i);
		}
		public List<? extends TerminalNode> COMMA() { return getTokens(SimpleSQLParser.COMMA); }
		public List<? extends TableSpecContext> tableSpec() {
			return getRuleContexts(TableSpecContext.class);
		}
		public TerminalNode COMMA(int i) {
			return getToken(SimpleSQLParser.COMMA, i);
		}
		public TableSpecListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableSpecList; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitTableSpecList(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final TableSpecListContext tableSpecList() throws RecognitionException {
		TableSpecListContext _localctx = new TableSpecListContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_tableSpecList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256); tableSpec();
			setState(261);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(257); match(COMMA);
				setState(258); tableSpec();
				}
				}
				setState(263);
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

	public static class TableSpecContext extends ParserRuleContext {
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public AliasContext alias() {
			return getRuleContext(AliasContext.class,0);
		}
		public TerminalNode INNER() { return getToken(SimpleSQLParser.INNER, 0); }
		public TableSpecContext tableSpec() {
			return getRuleContext(TableSpecContext.class,0);
		}
		public TerminalNode JOIN() { return getToken(SimpleSQLParser.JOIN, 0); }
		public TableSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableSpec; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitTableSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final TableSpecContext tableSpec() throws RecognitionException {
		TableSpecContext _localctx = new TableSpecContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_tableSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264); tableName();
			setState(266);
			_la = _input.LA(1);
			if (_la==AS || _la==ID) {
				{
				setState(265); alias();
				}
			}

			setState(273);
			_la = _input.LA(1);
			if (_la==INNER || _la==JOIN) {
				{
				setState(269);
				_la = _input.LA(1);
				if (_la==INNER) {
					{
					setState(268); match(INNER);
					}
				}

				setState(271); match(JOIN);
				setState(272); tableSpec();
				}
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

	public static class AggregateContext extends ParserRuleContext {
		public TerminalNode COUNT() { return getToken(SimpleSQLParser.COUNT, 0); }
		public TerminalNode R_PAREN() { return getToken(SimpleSQLParser.R_PAREN, 0); }
		public ColumnSpecContext columnSpec() {
			return getRuleContext(ColumnSpecContext.class,0);
		}
		public TerminalNode L_PAREN() { return getToken(SimpleSQLParser.L_PAREN, 0); }
		public TerminalNode SUM() { return getToken(SimpleSQLParser.SUM, 0); }
		public AggregateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregate; }
		@Override
		public <Result> Result accept(ParseTreeVisitor<? extends Result> visitor) {
			if ( visitor instanceof SimpleSQLVisitor<?> ) return ((SimpleSQLVisitor<? extends Result>)visitor).visitAggregate(this);
			else return visitor.visitChildren(this);
		}
	}

	@RuleVersion(0)
	public final AggregateContext aggregate() throws RecognitionException {
		AggregateContext _localctx = new AggregateContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_aggregate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			switch (_input.LA(1)) {
			case COUNT:
				{
				{
				setState(275); match(COUNT);
				setState(276); match(L_PAREN);
				{
				setState(277); columnSpec();
				}
				}
				}
				break;
			case SUM:
				{
				{
				setState(278); match(SUM);
				setState(279); match(L_PAREN);
				setState(280); columnSpec();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(283); match(R_PAREN);
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
		"\3\ub6d5\u5d61\uf22c\uad89\u44d2\udf97\u846a\ue419\3;\u0120\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\3\5\3F\n\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\5\7U\n\7\3\b\5\bX\n\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\r\7\rg\n\r\f\r\16\rj\13\r\3\16\3\16\3\16\7\16o\n\16\f\16\16"+
		"\16r\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17}\n\17\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u0084\n\20\3\20\5\20\u0087\n\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\5\21\u008f\n\21\3\22\5\22\u0092\n\22\3\22\3\22\5\22"+
		"\u0096\n\22\7\22\u0098\n\22\f\22\16\22\u009b\13\22\3\22\3\22\3\23\3\23"+
		"\5\23\u00a1\n\23\3\24\3\24\5\24\u00a5\n\24\3\25\3\25\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\7\26\u00b0\n\26\f\26\16\26\u00b3\13\26\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\5\27\u00bb\n\27\3\27\5\27\u00be\n\27\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\5\30\u00c6\n\30\3\31\3\31\3\31\3\31\5\31\u00cc\n\31\3"+
		"\31\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u00d5\n\32\f\32\16\32\u00d8\13"+
		"\32\3\32\3\32\3\33\3\33\3\33\3\33\7\33\u00e0\n\33\f\33\16\33\u00e3\13"+
		"\33\3\33\3\33\3\34\3\34\3\34\5\34\u00ea\n\34\3\34\3\34\3\34\3\34\5\34"+
		"\u00f0\n\34\3\35\3\35\3\35\7\35\u00f5\n\35\f\35\16\35\u00f8\13\35\3\36"+
		"\3\36\3\36\5\36\u00fd\n\36\3\36\3\36\5\36\u0101\n\36\3\37\3\37\3\37\7"+
		"\37\u0106\n\37\f\37\16\37\u0109\13\37\3 \3 \5 \u010d\n \3 \5 \u0110\n"+
		" \3 \3 \5 \u0114\n \3!\3!\3!\3!\3!\3!\5!\u011c\n!\3!\3!\3!\2\2\2\"\2\2"+
		"\4\2\6\2\b\2\n\2\f\2\16\2\20\2\22\2\24\2\26\2\30\2\32\2\34\2\36\2 \2\""+
		"\2$\2&\2(\2*\2,\2.\2\60\2\62\2\64\2\66\28\2:\2<\2>\2@\2\2\6\3\2*/\3\2"+
		"!\"\3\2\678\3\2\17\20\u0123\2B\3\2\2\2\4E\3\2\2\2\6I\3\2\2\2\bK\3\2\2"+
		"\2\nM\3\2\2\2\fT\3\2\2\2\16W\3\2\2\2\20[\3\2\2\2\22]\3\2\2\2\24_\3\2\2"+
		"\2\26a\3\2\2\2\30c\3\2\2\2\32k\3\2\2\2\34|\3\2\2\2\36\u0086\3\2\2\2 \u008e"+
		"\3\2\2\2\"\u0091\3\2\2\2$\u00a0\3\2\2\2&\u00a4\3\2\2\2(\u00a6\3\2\2\2"+
		"*\u00a8\3\2\2\2,\u00b6\3\2\2\2.\u00c5\3\2\2\2\60\u00c7\3\2\2\2\62\u00d0"+
		"\3\2\2\2\64\u00db\3\2\2\2\66\u00e6\3\2\2\28\u00f1\3\2\2\2:\u00fc\3\2\2"+
		"\2<\u0102\3\2\2\2>\u010a\3\2\2\2@\u011b\3\2\2\2BC\t\2\2\2C\3\3\2\2\2D"+
		"F\t\3\2\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\7\63\2\2H\5\3\2\2\2IJ\7\64\2"+
		"\2J\7\3\2\2\2KL\7\65\2\2L\t\3\2\2\2MN\7\67\2\2N\13\3\2\2\2OU\5\4\3\2P"+
		"U\5\6\4\2QU\5\b\5\2RU\5\n\6\2SU\7\32\2\2TO\3\2\2\2TP\3\2\2\2TQ\3\2\2\2"+
		"TR\3\2\2\2TS\3\2\2\2U\r\3\2\2\2VX\7\7\2\2WV\3\2\2\2WX\3\2\2\2XY\3\2\2"+
		"\2YZ\78\2\2Z\17\3\2\2\2[\\\t\4\2\2\\\21\3\2\2\2]^\t\4\2\2^\23\3\2\2\2"+
		"_`\7\63\2\2`\25\3\2\2\2ab\t\5\2\2b\27\3\2\2\2ch\5\32\16\2de\7\30\2\2e"+
		"g\5\32\16\2fd\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2i\31\3\2\2\2jh\3\2"+
		"\2\2kp\5\34\17\2lm\7\31\2\2mo\5\34\17\2nl\3\2\2\2or\3\2\2\2pn\3\2\2\2"+
		"pq\3\2\2\2q\33\3\2\2\2rp\3\2\2\2st\5 \21\2tu\5\36\20\2u}\3\2\2\2vw\7\27"+
		"\2\2w}\5\34\17\2xy\7 \2\2yz\5\30\r\2z{\7\37\2\2{}\3\2\2\2|s\3\2\2\2|v"+
		"\3\2\2\2|x\3\2\2\2}\35\3\2\2\2~\177\5\2\2\2\177\u0080\5 \21\2\u0080\u0087"+
		"\3\2\2\2\u0081\u0083\7\26\2\2\u0082\u0084\7\27\2\2\u0083\u0082\3\2\2\2"+
		"\u0083\u0084\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0087\7\32\2\2\u0086~\3"+
		"\2\2\2\u0086\u0081\3\2\2\2\u0087\37\3\2\2\2\u0088\u008f\5\f\7\2\u0089"+
		"\u008f\5:\36\2\u008a\u008b\7 \2\2\u008b\u008c\5 \21\2\u008c\u008d\7\37"+
		"\2\2\u008d\u008f\3\2\2\2\u008e\u0088\3\2\2\2\u008e\u0089\3\2\2\2\u008e"+
		"\u008a\3\2\2\2\u008f!\3\2\2\2\u0090\u0092\5$\23\2\u0091\u0090\3\2\2\2"+
		"\u0091\u0092\3\2\2\2\u0092\u0099\3\2\2\2\u0093\u0095\7\33\2\2\u0094\u0096"+
		"\5$\23\2\u0095\u0094\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098\3\2\2\2\u0097"+
		"\u0093\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2"+
		"\2\2\u009a\u009c\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u009d\7\2\2\3\u009d"+
		"#\3\2\2\2\u009e\u00a1\5&\24\2\u009f\u00a1\5(\25\2\u00a0\u009e\3\2\2\2"+
		"\u00a0\u009f\3\2\2\2\u00a1%\3\2\2\2\u00a2\u00a5\5\60\31\2\u00a3\u00a5"+
		"\5\66\34\2\u00a4\u00a2\3\2\2\2\u00a4\u00a3\3\2\2\2\u00a5\'\3\2\2\2\u00a6"+
		"\u00a7\5*\26\2\u00a7)\3\2\2\2\u00a8\u00a9\7\13\2\2\u00a9\u00aa\7\f\2\2"+
		"\u00aa\u00ab\5\20\t\2\u00ab\u00ac\7 \2\2\u00ac\u00b1\5,\27\2\u00ad\u00ae"+
		"\7\36\2\2\u00ae\u00b0\5,\27\2\u00af\u00ad\3\2\2\2\u00b0\u00b3\3\2\2\2"+
		"\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4\3\2\2\2\u00b3\u00b1"+
		"\3\2\2\2\u00b4\u00b5\7\37\2\2\u00b5+\3\2\2\2\u00b6\u00b7\5\22\n\2\u00b7"+
		"\u00ba\5.\30\2\u00b8\u00b9\7\r\2\2\u00b9\u00bb\7\16\2\2\u00ba\u00b8\3"+
		"\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bd\3\2\2\2\u00bc\u00be\5\26\f\2\u00bd"+
		"\u00bc\3\2\2\2\u00bd\u00be\3\2\2\2\u00be-\3\2\2\2\u00bf\u00c6\7\3\2\2"+
		"\u00c0\u00c1\7\4\2\2\u00c1\u00c2\7 \2\2\u00c2\u00c3\5\24\13\2\u00c3\u00c4"+
		"\7\37\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00bf\3\2\2\2\u00c5\u00c0\3\2\2\2"+
		"\u00c6/\3\2\2\2\u00c7\u00c8\7\21\2\2\u00c8\u00c9\7\22\2\2\u00c9\u00cb"+
		"\5\20\t\2\u00ca\u00cc\5\62\32\2\u00cb\u00ca\3\2\2\2\u00cb\u00cc\3\2\2"+
		"\2\u00cc\u00cd\3\2\2\2\u00cd\u00ce\7\23\2\2\u00ce\u00cf\5\64\33\2\u00cf"+
		"\61\3\2\2\2\u00d0\u00d1\7 \2\2\u00d1\u00d6\5\22\n\2\u00d2\u00d3\7\36\2"+
		"\2\u00d3\u00d5\5\22\n\2\u00d4\u00d2\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6"+
		"\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00d6\3\2"+
		"\2\2\u00d9\u00da\7\37\2\2\u00da\63\3\2\2\2\u00db\u00dc\7 \2\2\u00dc\u00e1"+
		"\5\f\7\2\u00dd\u00de\7\36\2\2\u00de\u00e0\5\f\7\2\u00df\u00dd\3\2\2\2"+
		"\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e4"+
		"\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\7\37\2\2\u00e5\65\3\2\2\2\u00e6"+
		"\u00e9\7\5\2\2\u00e7\u00ea\58\35\2\u00e8\u00ea\5@!\2\u00e9\u00e7\3\2\2"+
		"\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec\7\6\2\2\u00ec\u00ef"+
		"\5<\37\2\u00ed\u00ee\7\n\2\2\u00ee\u00f0\5\30\r\2\u00ef\u00ed\3\2\2\2"+
		"\u00ef\u00f0\3\2\2\2\u00f0\67\3\2\2\2\u00f1\u00f6\5:\36\2\u00f2\u00f3"+
		"\7\36\2\2\u00f3\u00f5\5:\36\2\u00f4\u00f2\3\2\2\2\u00f5\u00f8\3\2\2\2"+
		"\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f79\3\2\2\2\u00f8\u00f6\3"+
		"\2\2\2\u00f9\u00fa\5\20\t\2\u00fa\u00fb\7\35\2\2\u00fb\u00fd\3\2\2\2\u00fc"+
		"\u00f9\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe\u0101\5\22"+
		"\n\2\u00ff\u0101\7#\2\2\u0100\u00fe\3\2\2\2\u0100\u00ff\3\2\2\2\u0101"+
		";\3\2\2\2\u0102\u0107\5> \2\u0103\u0104\7\36\2\2\u0104\u0106\5> \2\u0105"+
		"\u0103\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2"+
		"\2\2\u0108=\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010c\5\20\t\2\u010b\u010d"+
		"\5\16\b\2\u010c\u010b\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u0113\3\2\2\2"+
		"\u010e\u0110\7\b\2\2\u010f\u010e\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0111"+
		"\3\2\2\2\u0111\u0112\7\t\2\2\u0112\u0114\5> \2\u0113\u010f\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114?\3\2\2\2\u0115\u0116\7\24\2\2\u0116\u0117\7 \2\2"+
		"\u0117\u011c\5:\36\2\u0118\u0119\7\25\2\2\u0119\u011a\7 \2\2\u011a\u011c"+
		"\5:\36\2\u011b\u0115\3\2\2\2\u011b\u0118\3\2\2\2\u011c\u011d\3\2\2\2\u011d"+
		"\u011e\7\37\2\2\u011eA\3\2\2\2!ETWhp|\u0083\u0086\u008e\u0091\u0095\u0099"+
		"\u00a0\u00a4\u00b1\u00ba\u00bd\u00c5\u00cb\u00d6\u00e1\u00e9\u00ef\u00f6"+
		"\u00fc\u0100\u0107\u010c\u010f\u0113\u011b";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
	}
}