package com.gmail.chh9513136.simpledb.core;

import com.gmail.chh9513136.simpledb.expr.ColumnSpec;

public class Aggregate {

    public static final int COUNT = 0;
    public static final int SUM = 1;
    
    public final int type;
    public final ColumnSpec column;
    
    public Aggregate(int type, ColumnSpec column) {
        this.type = type;
        this.column = column;
    }
    
}
