package com.gmail.chh9513136.simpledb.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.mapdb.BTreeMap;
import org.mapdb.HTreeMap;

import com.gmail.chh9513136.simpledb.compiler.SilentErrorListener;

public class InsertIntoOperation implements SimpleSQLOperation {
    String tableName;
    List<Comparable> valueList;
    List<String> columnList;

    public InsertIntoOperation(String tableName, List<String> columnList, List<Comparable> valueList) {
        this.tableName = tableName;
        this.valueList = Collections.unmodifiableList(valueList);
        this.columnList = columnList;
    }

    @Override
    public Object execute(SilentErrorListener errListener, Map<String, Table> tableList) {
        Table t = tableList.get(tableName.toLowerCase());
        if (t == null) {
            errListener.appendLine("Error: Table does not exist");
            return null;
        }
        
        try {
            Tuple newTuple = new Tuple(t, columnList, valueList);
            t.addTuple(newTuple);
        } catch (Exception e) {
            errListener.appendLine(e.getMessage());
            //e.printStackTrace();
        }
        

        return null;
    }

}
