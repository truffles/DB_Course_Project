// Generated from C:\Users\Tlaloc\Desktop\Recognizer\SimpleSQL.g4 by ANTLR 4.x
package com.gmail.chh9513136.simpledb.compiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.gmail.chh9513136.simpledb.DbInternalException;
import com.gmail.chh9513136.simpledb.compiler.SimpleSQLParser.IndexTypeContext;
import com.gmail.chh9513136.simpledb.core.Aggregate;
import com.gmail.chh9513136.simpledb.core.ColumnDef;
import com.gmail.chh9513136.simpledb.core.CreateTableOperation;
import com.gmail.chh9513136.simpledb.core.InsertIntoOperation;
import com.gmail.chh9513136.simpledb.core.SelectOperation;
import com.gmail.chh9513136.simpledb.core.Datatype;
import com.gmail.chh9513136.simpledb.core.Datatype.IntDatatype;
import com.gmail.chh9513136.simpledb.core.Datatype.VarcharDatatype;
import com.gmail.chh9513136.simpledb.core.SimpleSQLOperation;
import com.gmail.chh9513136.simpledb.expr.AndExpr;
import com.gmail.chh9513136.simpledb.expr.ColumnSpec;
import com.gmail.chh9513136.simpledb.expr.CompareExpr;
import com.gmail.chh9513136.simpledb.expr.Expr;
import com.gmail.chh9513136.simpledb.expr.LiteralExpr;
import com.gmail.chh9513136.simpledb.expr.OrExpr;
import com.gmail.chh9513136.simpledb.expr.TableSpec;


public class SimpleSQLVisitorImpl extends AbstractParseTreeVisitor<Object> implements SimpleSQLVisitor<Object> {
    
    private SilentErrorListener errListener;
    
    public SimpleSQLVisitorImpl(SilentErrorListener listener) {
        super();
        errListener = listener;
    }

    @Override
    public Integer visitCompare(@NotNull SimpleSQLParser.CompareContext ctx) {
        if (ctx.GT() != null) {
            return Integer.valueOf(CompareExpr.GT);
        } else if (ctx.GE() != null) {
            return Integer.valueOf(CompareExpr.GE);
        } else if (ctx.LT() != null) {
            return Integer.valueOf(CompareExpr.LT);
        } else if (ctx.LE() != null) {
            return Integer.valueOf(CompareExpr.LE);
        } else if (ctx.EQ() != null) {
            return Integer.valueOf(CompareExpr.EQ);
        } else if (ctx.NEQ() != null) {
            return Integer.valueOf(CompareExpr.NEQ);
        }
        throw new DbInternalException("Parser BUG: Compare");
    }


    @Override
    public ColumnDef visitColumnDefinition(@NotNull SimpleSQLParser.ColumnDefinitionContext ctx) {
        String columnName = visitColumnName(ctx.columnName());
        Datatype datatype = visitDatatype(ctx.datatype());
        boolean isPrimaryKey = (ctx.PRIMARY() != null);
        int indexType = visitIndexType(ctx.indexType());
        
        return new ColumnDef(columnName, datatype, isPrimaryKey, indexType);
    }


    @Override
    public String visitTableName(@NotNull SimpleSQLParser.TableNameContext ctx) {
        return ctx.getText();
    }


    @Override
    public Datatype visitDatatype(@NotNull SimpleSQLParser.DatatypeContext ctx) {
        if (ctx.INT() != null)
            return IntDatatype.INSTANCE;
        else {
            int length;
            
            try {
            length = visitTypeLength(ctx.typeLength());
            } catch (NumberFormatException e) {
                length = Integer.MAX_VALUE;
            }
            
            if (length > 50) {
                TerminalNode node = ctx.typeLength().INT_NUM();
                errListener.appendLine(node.getText() + " is too large for varchar, expecting a value between 0 and 50.",
                        node.getSymbol());
                throw new RuntimeException();
            } else {
                return VarcharDatatype.newVarchar(length);
            }
        }
    }


    @Override
    public List<SimpleSQLOperation> visitStatementList(@NotNull SimpleSQLParser.StatementListContext ctx) {
        List<SimpleSQLOperation> operations = new ArrayList<>();
        try {
            for (SimpleSQLParser.StatementContext sctx : ctx.statement()) {
                operations.add(visitStatement(sctx));
            }
        } catch (RuntimeException e) {}
        return operations;
    }


    @Override
    public SimpleSQLOperation visitStatement(@NotNull SimpleSQLParser.StatementContext ctx) {
        return (SimpleSQLOperation) visit(ctx.getChild(0));
    }


    @Override
    public Expr visitTerm(@NotNull SimpleSQLParser.TermContext ctx) {
        if (ctx.L_PAREN() != null) {
            return visitTerm(ctx.term());
        }
        if (ctx.literalValue() != null) {
            return LiteralExpr.newLiteralExpr(visitLiteralValue(ctx.literalValue()));
        }
        ColumnSpec col = visitColumnSpec(ctx.columnSpec());
        if (col.columnName == null) {
            errListener.appendLine("* cannot be used in where clause", ctx.columnSpec().ASTER().getSymbol());
            throw new RuntimeException();
        }
        return col;
    }

    @Override
    public Expr visitExpression(@NotNull SimpleSQLParser.ExpressionContext ctx) {
        if (ctx.andCondition().size() == 1) {
            return visitAndCondition(ctx.andCondition(0));
        }
        
        List<Expr> orExprs = new ArrayList<>();
        
        for (SimpleSQLParser.AndConditionContext andCond : ctx.andCondition()) {
            orExprs.add((Expr) visitAndCondition(andCond));
        }
        
        return new OrExpr(orExprs);
    }


    @Override
    public Expr visitAndCondition(@NotNull SimpleSQLParser.AndConditionContext ctx) {
        if (ctx.condition().size() == 1) {
            return visitCondition(ctx.condition(0));
        }
        
        
        List<Expr> andExprs = new ArrayList<>();
        
        for (SimpleSQLParser.ConditionContext cond : ctx.condition()) {
            andExprs.add(visitCondition(cond));
        }
        
        return new AndExpr(andExprs);
    }


    @Override
    public List<String> visitColumnList(@NotNull SimpleSQLParser.ColumnListContext ctx) {
        List<String> columnNameList = new ArrayList<>();
        for (SimpleSQLParser.ColumnNameContext columnName : ctx.columnName()) {
            columnNameList.add(visitColumnName(columnName));
        }
        return columnNameList;
    }


    @Override
    public Integer visitTypeLength(@NotNull SimpleSQLParser.TypeLengthContext ctx) {
        return Integer.parseInt(ctx.getText());
    }


    @Override
    public Integer visitBitLiteral(@NotNull SimpleSQLParser.BitLiteralContext ctx) {
        Integer value;
        String toDecode = ctx.getText();
        
        if (toDecode.startsWith("0b")) {
            toDecode = toDecode.substring(2);
        } else {
            toDecode = toDecode.substring(2, toDecode.length() - 1);
        }
        
        try {
            value = Integer.parseInt(toDecode, 2);
        } catch (NumberFormatException e) {
            errListener.appendLine("bit-field value out of range", ctx.BIT_NUM().getSymbol());
            throw new RuntimeException();
        }
        return value;
    }


    @Override
    public Integer visitHexLiteral(@NotNull SimpleSQLParser.HexLiteralContext ctx) {
        Integer value;
        String toDecode = ctx.getText();
        
        if (toDecode.startsWith("0x")) {
            toDecode = toDecode.substring(2);
        } else {
            toDecode = toDecode.substring(2, toDecode.length() - 1);
        }
        
        try {
            value = Integer.parseInt(toDecode, 16);
        } catch (NumberFormatException e) {
            errListener.appendLine("hex value out of range", ctx.HEX_NUM().getSymbol());
            throw new RuntimeException();
        }
        return value;
    }


    @Override
    public Expr visitCondition(@NotNull SimpleSQLParser.ConditionContext ctx) {
        if (ctx.L_PAREN() != null) {
            return visitExpression(ctx.expression());
        } else if (ctx.NOT() != null) {
            Expr expr = visitCondition(ctx.condition());
            expr.negate();
            return expr;
        } else {
            Expr term = visitTerm(ctx.term());
            
            if (ctx.condRHS() != null) {
                CompareExpr right = visitCondRHS(ctx.condRHS());
                right.setLeft(term);
                return right;
            }
            return term;
        }
    }


    @Override
    public SimpleSQLOperation visitDataManipulateStmt(@NotNull SimpleSQLParser.DataManipulateStmtContext ctx) {
        if (ctx.selectStmt() != null) {
            return visitSelectStmt(ctx.selectStmt());
        }
        return visitInsertIntoStmt(ctx.insertIntoStmt());
    }


    @Override
    public String visitStringLiteral(@NotNull SimpleSQLParser.StringLiteralContext ctx) {
        return String.valueOf(ctx.getText());
    }


    @Override
    public List<Comparable> visitValueList(@NotNull SimpleSQLParser.ValueListContext ctx) {
        List<Comparable> valueList = new ArrayList<>();
        for (SimpleSQLParser.LiteralValueContext c : ctx.literalValue()) {
            valueList.add(visitLiteralValue(c));
        }
        return valueList;
    }


    @SuppressWarnings("unchecked")
    @Override
    public InsertIntoOperation visitInsertIntoStmt(@NotNull SimpleSQLParser.InsertIntoStmtContext ctx) {
        String tablename = visitTableName(ctx.tableName());
        List<String> columnList;
        
        if (ctx.columnList() != null) {
            columnList = visitColumnList(ctx.columnList());
        } else {
            columnList = Collections.EMPTY_LIST; // EMPTY LIST means DEFAULT COLUMNS
        }
        
        List<Comparable> valueList = visitValueList(ctx.valueList());
        
        return new InsertIntoOperation(tablename, columnList, valueList);
    }


    @Override
    public SimpleSQLOperation visitDataDefinitionStmt(@NotNull SimpleSQLParser.DataDefinitionStmtContext ctx) {
        return visitCreateTableStmt(ctx.createTableStmt()); // assume CREATE TABLE (for stage I)
    }


    @Override
    public CreateTableOperation visitCreateTableStmt(@NotNull SimpleSQLParser.CreateTableStmtContext ctx) {
        String tablename = visitTableName(ctx.tableName());
        List<ColumnDef> columnDefList = new ArrayList<ColumnDef>();
        
        for (SimpleSQLParser.ColumnDefinitionContext cctx : ctx.columnDefinition()) {
            ColumnDef cdef = visitColumnDefinition(cctx);
            columnDefList.add(cdef);
        }
        
        return new CreateTableOperation(tablename, columnDefList);
    }


    @Override
    public Integer visitNumberLiteral(@NotNull SimpleSQLParser.NumberLiteralContext ctx) {
        Integer value;
        
        try {
            value = Integer.parseInt(ctx.getText());
        } catch (NumberFormatException e) {
            Token sign = ctx.sign;
            
            if (sign != null) {
                errListener.appendLine("integer value out of range", sign, ctx.INT_NUM().getSymbol());
            } else {
                errListener.appendLine("integer value out of range", ctx.INT_NUM().getSymbol());
            }
            
            throw new RuntimeException();
        }
        return value;
    }


    @Override
    public Comparable visitLiteralValue(@NotNull SimpleSQLParser.LiteralValueContext ctx) {
        if (ctx.NULL() != null)
            return Datatype.NullDatatype.INSTANCE;
        if (ctx.numberLiteral() != null)
            return visitNumberLiteral(ctx.numberLiteral());
        if (ctx.hexLiteral() != null)
            return visitHexLiteral(ctx.hexLiteral());
        if (ctx.bitLiteral() != null)
            return visitBitLiteral(ctx.bitLiteral());
        if (ctx.stringLiteral() != null)
            return visitStringLiteral(ctx.stringLiteral());
        
        errListener.appendLine("BUG at visitLiteralValue", ctx.getStart());
        throw new RuntimeException();
    }


    @Override
    public String visitColumnName(@NotNull SimpleSQLParser.ColumnNameContext ctx) {
        return ctx.getText();
    }

    @Override
    public List<TableSpec> visitTableSpecList(@NotNull SimpleSQLParser.TableSpecListContext ctx) {
        List<TableSpec> list = new ArrayList<>();
        
        for (SimpleSQLParser.TableSpecContext elem : ctx.tableSpec()) {
            list.add(visitTableSpec(elem));
        }
        
        return list;
    }

    @Override
    public Aggregate visitAggregate(@NotNull SimpleSQLParser.AggregateContext ctx) {
        SimpleSQLParser.ColumnSpecContext cctx = ctx.columnSpec();
        ColumnSpec col = (cctx == null)? null : visitColumnSpec(cctx);
        
        if (ctx.COUNT() != null) {
            return new Aggregate(Aggregate.COUNT, col);
        } else {
            return new Aggregate(Aggregate.SUM, col);
        }
    }

    @Override
    public TableSpec visitTableSpec(@NotNull SimpleSQLParser.TableSpecContext ctx) {
        String tableName = visitTableName(ctx.tableName()).toLowerCase();
        String alias = null;
        TableSpec join = null;
        
        if (ctx.alias() != null) {
            alias = visitAlias(ctx.alias()).toLowerCase();
        }
        
        if (ctx.tableSpec() != null) {
            join = visitTableSpec(ctx.tableSpec());
        }
        
        return new TableSpec(tableName, alias, join);
    }

    @Override
    public CompareExpr visitCondRHS(@NotNull SimpleSQLParser.CondRHSContext ctx) {
        if (ctx.IS() != null) {
            CompareExpr expr;
            
            if (ctx.NOT() != null) {
                expr = new CompareExpr(CompareExpr.NEQ);
            } else {
                expr = new CompareExpr(CompareExpr.EQ);
            }
            expr.setRight(LiteralExpr.NULLEXPR);
            return expr;
            
        } else {
            int type = visitCompare(ctx.compare());
            CompareExpr expr = new CompareExpr(type);
            
            Expr right = visitTerm(ctx.term());
            expr.setRight(right);
            return expr;
        }
    }

    @Override
    public String visitAlias(@NotNull SimpleSQLParser.AliasContext ctx) {
        return ctx.ID().getText();
    }

    @Override
    public SimpleSQLOperation visitSelectStmt(@NotNull SimpleSQLParser.SelectStmtContext ctx) {
        
        List<ColumnSpec> selectList = null; // either this
        Aggregate aggregate = null;         // or this
        List<TableSpec> fromList;
        Expr whereExpr = null;
        
        // SELECT
        if (ctx.selectList() != null) {
            selectList = visitSelectList(ctx.selectList());
        } else { // aggregate
            aggregate = visitAggregate(ctx.aggregate());
        }
        
        // FROM
        fromList = visitTableSpecList(ctx.tableSpecList());
        
        // WHERE
        if (ctx.expression() != null) {
            whereExpr = visitExpression(ctx.expression());
        }
        
        return new SelectOperation(selectList, aggregate, fromList, whereExpr);
    }

    @Override
    public ColumnSpec visitColumnSpec(@NotNull SimpleSQLParser.ColumnSpecContext ctx) {
        SimpleSQLParser.ColumnNameContext cctx = ctx.columnName();
        SimpleSQLParser.TableNameContext tctx = ctx.tableName();
        String columnName = (cctx == null)? null : visitColumnName(cctx).toLowerCase();
        String tableName = (tctx == null)? null : visitTableName(tctx).toLowerCase();
        
        return new ColumnSpec(tableName, columnName);
    }

    @Override
    public List<ColumnSpec> visitSelectList(@NotNull SimpleSQLParser.SelectListContext ctx) {

        List<ColumnSpec> list = new ArrayList<>();
        
        for (SimpleSQLParser.ColumnSpecContext c : ctx.columnSpec()) {
            list.add(visitColumnSpec(c));
        }
        
        return list;
    }

    @Override
    public Integer visitIndexType(IndexTypeContext ctx) {
        if (ctx == null) return 0;
        if (ctx.HASH() != null) return ColumnDef.HASH_IDX;
        if (ctx.BTREE() != null) return ColumnDef.TREE_IDX;
        return 0;
    }
}