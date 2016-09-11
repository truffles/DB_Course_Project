package com.gmail.chh9513136.simpledb.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tuple implements Serializable, Comparable<Tuple> {
    /**
     * 
     */
    private static final long serialVersionUID = -2613088862599393692L;
    private final List<Attr> attrList = new ArrayList<>();
    private int pkIndex = -1;
    public int autoIncKey = 0;
   
    
    @SuppressWarnings("rawtypes")
    public Tuple(Table table, List<String> columnList, List<Comparable> valueList) throws Exception {
        
        
         Map<String, Comparable> colValMap = null;
        if (columnList.isEmpty()) {
            if (table.columnDefList.size() != valueList.size()) {
                throw new Exception(
                        "Error: Mismatched number of values, expected " + table.columnDefList.size() + ", got " + valueList.size());
            }
            /*colValMap = new HashMap<>();
            for(int i = 0; i < table.columnDefList.size(); i++){
                String attrName = table.columnDefList.get(i).columnName;
                colValMap.put(attrName.toLowerCase(), valueList.get(i));
            }*/
        } else if (columnList.size() != valueList.size()) {
            throw new Exception(
                    "Error: Mismatched number of values, expected " + columnList.size() + ", got " + valueList.size());
        } else {
            colValMap = new HashMap<>();
            for (int i = 0; i < columnList.size(); i++) {
                String attrName = columnList.get(i);
                if (colValMap.put(attrName.toLowerCase(), valueList.get(i)) != null) {
                    throw new Exception("Error: Duplicate attrbute name \"" + columnList.get(i) + "\"");
                }
                if (table.columnDefMap.containsKey(attrName.toLowerCase()) == false) {
                    throw new Exception("Error: Attribute \"" + columnList.get(i) + "\" does not exist");
                }
            }
        }
        
        for (int i = 0; i < table.columnDefList.size(); i++) {
            ColumnDef column = table.columnDefList.get(i);
            Comparable value;

            if (colValMap == null) {
                value = valueList.get(i);
            } else {
                value = colValMap.get(column.columnName.toLowerCase());
                if (value == null)
                    value = Datatype.NullDatatype.INSTANCE;
            }

            if (column.isPrimaryKey) {
                pkIndex = i;
            }

            Attr attr = column.datatype.newAttr(value);
            if (attr != null) {
                attrList.add(attr);
            } else {
                String gotType;
                if (value.getClass() == Integer.class) {
                    gotType = "INT";
                } else if (value.getClass() == String.class) {
                    gotType = "VARCHAR of length " + ((String) value).length();
                } else {
                    gotType = "NULL";
                }

                throw new Exception("Error: Datatype mismatch in attribute \"" + column.columnName + "\": expected " + column.datatype.toString() + ", got " + gotType);
            }
        }
    }
    
    public int getAttrSize() {
        return attrList.size();
    }
    
    public Attr getAttr(int index) {
        return attrList.get(index);
    }
    
    @SuppressWarnings("rawtypes")
    public Comparable getPrimaryKey() {
        if (pkIndex >= 0)
            return attrList.get(pkIndex);
        return autoIncKey;
    }

    public String toString() {
        StringBuilder temp = new StringBuilder("|");
        for (int i = 0; i < attrList.size(); i++) {
            temp.append(attrList.get(i).toString(20));
        }
        return temp.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(Tuple o) {
        if (this == o)
            return 0;
        return getPrimaryKey().compareTo(o.getPrimaryKey());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attrList == null) ? 0 : attrList.hashCode());
        result = prime * result + autoIncKey;
        result = prime * result + pkIndex;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple other = (Tuple) obj;
        if (attrList == null) {
            if (other.attrList != null) {
                return false;
            }
        } else if (!attrList.equals(other.attrList)) {
            return false;
        }
        if (autoIncKey != other.autoIncKey) {
            return false;
        }
        if (pkIndex != other.pkIndex) {
            return false;
        }
        return true;
    }
}
