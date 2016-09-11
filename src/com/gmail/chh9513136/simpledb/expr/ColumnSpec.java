package com.gmail.chh9513136.simpledb.expr;

import com.gmail.chh9513136.simpledb.DbInternalException;

public class ColumnSpec extends Expr {

    public String tableName;
    public final String columnName;
    
    private ColumnSpec() {columnName = null;}
    
    public ColumnSpec(String tableName, String columnName) {
    	this.tableName = (tableName!=null)?tableName.toLowerCase():tableName;
    	this.columnName = (columnName!=null)?columnName.toLowerCase():columnName;
        
    }

    @Override
    public void negate() {
        throw new DbInternalException("Unsupported operation: ColumnSpec.negate()");
    }

    @Override
    public boolean isNegate() {
        return false;
    }
}
