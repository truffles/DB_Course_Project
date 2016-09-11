package com.gmail.chh9513136.simpledb.core;

public class CompareLiteral {
    public static final int INT = 1;
    public static final int COLDEF = 2;
    public static final int STR = 3;
    int num;
    String tableName;
    String attrName;
    String JustString;
    final int type;

    public CompareLiteral(int num) {
        this.num = num;
        this.type = INT;
    }

    public CompareLiteral(String tableName, String attrName) {
        this.tableName = tableName;
        this.attrName = attrName;
        this.type = COLDEF;
    }

    public CompareLiteral(String str) {
        this.JustString = str;
        this.type = STR;
    }

    public Object getEle() {
        if (type == INT)
            return this.num;
        else if (type == COLDEF) {
            String[] str = new String[2];
            str[0] = this.tableName;
            str[1] = this.attrName;
            return str;
        } else if (type == STR)
            return this.JustString;
        else
            return null;
    }
}
