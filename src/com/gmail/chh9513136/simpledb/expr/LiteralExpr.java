package com.gmail.chh9513136.simpledb.expr;

import com.gmail.chh9513136.simpledb.DbInternalException;
import com.gmail.chh9513136.simpledb.core.Datatype;

public class LiteralExpr extends Expr {

    public static final int NULL = -1;
    public static final int INTEGER = 0;
    public static final int STRING = 1;
    
    public static final LiteralExpr NULLEXPR = new LiteralExpr();
    
    private final int type;
    public final Object value;
    
    private LiteralExpr() {
        this.type = NULL;
        this.value = null;
    }
    
    public LiteralExpr(int value) {
        this.type = INTEGER;
        this.value = Integer.valueOf(value);
    }
    
    public LiteralExpr(String value) {
        this.type = STRING;
        this.value = value;
    }
    
    public static LiteralExpr newLiteralExpr(Object value) {
        if (value == null || value == Datatype.NullDatatype.INSTANCE) {
            return NULLEXPR;
        } else if (value instanceof Integer) {
            return new LiteralExpr((int) value);
        } else if (value instanceof String) {
            return new LiteralExpr((String) value);
        }
        throw new DbInternalException("Constructor Error: newLiteralExpr()");
    }
    
    @Override
    public void negate() {
        throw new DbInternalException("Unsupported operation: LiteralExpr.negate()");
    }

    @Override
    public boolean isNegate() {
        return false;
    }

    public int getType() {
        return type;
    }

}
