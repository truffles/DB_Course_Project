package com.gmail.chh9513136.simpledb.expr;

public abstract class Expr {

    private boolean neg = false;
    
    public void negate() {
        neg = !neg;
    }
    
    public boolean isNegate() {
        return neg;
    }
}
