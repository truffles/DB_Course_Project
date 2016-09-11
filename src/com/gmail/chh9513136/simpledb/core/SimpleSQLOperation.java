package com.gmail.chh9513136.simpledb.core;

import java.util.Map;
import com.gmail.chh9513136.simpledb.compiler.SilentErrorListener;

public interface SimpleSQLOperation {
    
    public Object execute( SilentErrorListener errListener, Map<String, Table> tableList );

}
