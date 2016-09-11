package com.gmail.chh9513136.simpledb;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Map;

import com.gmail.chh9513136.simpledb.core.Table;

public final class Static {
    
    public static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
    
    public static void writeTables(Map<String, Table> map, ObjectOutputStream o) throws IOException {
        o.writeObject(map);
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Table> readTables(ObjectInputStream o) throws ClassNotFoundException, IOException {
        return (Map<String, Table>) o.readObject();
    }
}
