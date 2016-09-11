package com.gmail.chh9513136.simpledb.expr;

import com.gmail.chh9513136.simpledb.DbInternalException;

public class TableSpec extends Expr {

    public final String tableName;
    public final String alias;
    public final TableSpec join;
    
    public TableSpec(String tableName, String alias, TableSpec join) {
        this.tableName = (tableName!=null)?tableName.toLowerCase():tableName;
        this.alias = (alias!=null)?alias.toLowerCase():alias;
        this.join = join;
    }
    
    @Override
    public void negate() {
        throw new DbInternalException("Unsupported operation: TableSpec.negate()");
    }

    @Override
    public boolean isNegate() {
        return false;
    }
    
}
