// Generated from D:\DB\Recognizer\SimpleSQL.g4 by ANTLR 4.x
package com.gmail.chh9513136.simpledb.compiler;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleSQLParser}.
 *
 * @param <Result> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpleSQLVisitor<Result> extends ParseTreeVisitor<Result> {
	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCompare(@NotNull SimpleSQLParser.CompareContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#tableSpecList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitTableSpecList(@NotNull SimpleSQLParser.TableSpecListContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitColumnDefinition(@NotNull SimpleSQLParser.ColumnDefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitTableName(@NotNull SimpleSQLParser.TableNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#aggregate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAggregate(@NotNull SimpleSQLParser.AggregateContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#tableSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitTableSpec(@NotNull SimpleSQLParser.TableSpecContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#condRHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCondRHS(@NotNull SimpleSQLParser.CondRHSContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#datatype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDatatype(@NotNull SimpleSQLParser.DatatypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#statementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitStatementList(@NotNull SimpleSQLParser.StatementListContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitStatement(@NotNull SimpleSQLParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#alias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAlias(@NotNull SimpleSQLParser.AliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitTerm(@NotNull SimpleSQLParser.TermContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExpression(@NotNull SimpleSQLParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#andCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAndCondition(@NotNull SimpleSQLParser.AndConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#columnList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitColumnList(@NotNull SimpleSQLParser.ColumnListContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#typeLength}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitTypeLength(@NotNull SimpleSQLParser.TypeLengthContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#bitLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBitLiteral(@NotNull SimpleSQLParser.BitLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#hexLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitHexLiteral(@NotNull SimpleSQLParser.HexLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#selectStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSelectStmt(@NotNull SimpleSQLParser.SelectStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#columnSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitColumnSpec(@NotNull SimpleSQLParser.ColumnSpecContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#indexType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIndexType(@NotNull SimpleSQLParser.IndexTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCondition(@NotNull SimpleSQLParser.ConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#selectList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSelectList(@NotNull SimpleSQLParser.SelectListContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#dataManipulateStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDataManipulateStmt(@NotNull SimpleSQLParser.DataManipulateStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitStringLiteral(@NotNull SimpleSQLParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitValueList(@NotNull SimpleSQLParser.ValueListContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#insertIntoStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitInsertIntoStmt(@NotNull SimpleSQLParser.InsertIntoStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#dataDefinitionStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDataDefinitionStmt(@NotNull SimpleSQLParser.DataDefinitionStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#createTableStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCreateTableStmt(@NotNull SimpleSQLParser.CreateTableStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#numberLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNumberLiteral(@NotNull SimpleSQLParser.NumberLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#literalValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLiteralValue(@NotNull SimpleSQLParser.LiteralValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleSQLParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitColumnName(@NotNull SimpleSQLParser.ColumnNameContext ctx);
}