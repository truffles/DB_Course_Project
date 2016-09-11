package com.gmail.chh9513136.simpledb.expr;

public class CompareExpr extends Expr {
    
    public static final int GT = 0; // >
    public static final int LT = 2; // <
    public static final int GE = 3; // >=
    public static final int LE = 1; // <=
    public static final int EQ = 4; // =
    public static final int NEQ = 5; // !=
    
    private int type;
    private Expr left, right;
    
    public CompareExpr(int type) {
        this.type = type;
    }
    
    public void setLeft(Expr left) {
        this.left = left;
    }
    
    public void setRight(Expr right) {
        this.right = right;
    }
    
    public Expr getLeft() {
        return left;
    }
    
    public Expr getRight() {
        return right;
    }
    
    @Override
    public void negate() {
        type ^= 1;
    }
    @Override
    public boolean isNegate() {
        return false;
    }
    
    public int getType() {
        return type;
    }
    
}
