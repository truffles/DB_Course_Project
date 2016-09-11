package com.gmail.chh9513136.simpledb.expr;

import java.util.ArrayList;
import java.util.List;

public class AndExpr extends Expr {
    
    public final List<Expr> subExpr;
    
    public AndExpr(Expr... subExpr) {
        this.subExpr = new ArrayList<> ();
        
        for (Expr expr : subExpr) {
            this.subExpr.add(expr);
        }
    }
    
    public AndExpr(List<Expr> subExpr) {
        if (subExpr instanceof ArrayList) {
            this.subExpr = subExpr;
        } else {
            this.subExpr = new ArrayList<>(subExpr);
        }
    }
}
