package com.gmail.chh9513136.simpledb.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.gmail.chh9513136.simpledb.compiler.SilentErrorListener;

public class CreateTableOperation implements SimpleSQLOperation {
    
    public final String tableName;
    public final List<ColumnDef> columnDefList;
    
    public CreateTableOperation(String tableName, List<ColumnDef> columnDefList) {
        this.tableName = tableName;
        this.columnDefList = Collections.unmodifiableList(columnDefList);
    }

    @Override
    public Object execute( SilentErrorListener errListener, Map<String, Table> tableList ) {
        
    	if( tableList.containsKey(tableName.toLowerCase()) )
    	{
    		errListener.appendLine("Error: Table of the same name already exists");
    	}
    	else
    	{
    		try
    		{
    			Table newTable = new Table( tableName, columnDefList );
    			tableList.put( tableName.toLowerCase(), newTable );
    		}
    		catch(Exception e)
    		{
    			errListener.appendLine(e.getMessage());
    		}
    	}
        return null;
    }
    
}
