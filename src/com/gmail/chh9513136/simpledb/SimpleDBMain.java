package com.gmail.chh9513136.simpledb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.mapdb.BTreeMap;
import org.mapdb.HTreeMap;

import com.gmail.chh9513136.simpledb.compiler.SilentErrorListener;
import com.gmail.chh9513136.simpledb.compiler.SimpleSQLLexer;
import com.gmail.chh9513136.simpledb.compiler.SimpleSQLParser;
import com.gmail.chh9513136.simpledb.compiler.SimpleSQLVisitor;
import com.gmail.chh9513136.simpledb.compiler.SimpleSQLVisitorImpl;
import com.gmail.chh9513136.simpledb.core.ColumnDef;
import com.gmail.chh9513136.simpledb.core.SimpleSQLOperation;
import com.gmail.chh9513136.simpledb.core.Table;
import com.gmail.chh9513136.simpledb.core.Tuple;

public class SimpleDBMain {
    
    private static SimpleDBMain INSTANCE = null;
    
    private StringBuilder inputs = new StringBuilder();
    private CommonTokenStream tokens;
    private SilentErrorListener errorListener = new SilentErrorListener();
    
    private static Console console;
    private static BufferedReader reader;
    
    private Map<String, Table> tableList;
    private File tableFile = new File("data/tables.db");
    
    class ShutdownHook extends Thread {
        @Override
        public void run() {
            try {
                if (INSTANCE != null)
                    INSTANCE.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public SimpleDBMain() {
        
        if (tableFile.exists()) {
            FileInputStream fis;
            try {
                fis = new FileInputStream(tableFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                
                tableList = Static.readTables(ois);
                ois.close();
                
                for (Table table : tableList.values()) {
                    table.createOrReadBackDB();
                }
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            tableList = new HashMap<>();
        }
        
        INSTANCE = this;
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        
        while (true) {
            int lineNum = 1;
            System.out.print(" SQL 1> ");
            
            try {
                
                tokens = null;
                
                do {
                    readLine(inputs);
                    String input = inputs.toString();
                    errorListener.init(input);
                    
                    if (lineNum == 1) {
                        if (input.startsWith(".open")) {
                            String filename = input.substring(5).trim();
                            if (filename.isEmpty()) {
                                System.out.println("No filename specified");
                                input = "";
                                
                            } else {
                                input = readFromFile(filename);
                                tokens = lex(input);
                            }
                            break;
                        }
                        if (input.startsWith(".q")) {
                            System.exit(0);
                        }
                    }
                    
                    tokens = lex(input);
                    
                    int tokSize = tokens.size();
                    if (tokSize <= 1) {
                        tokens = null;
                        break;
                    }
                    else if (tokens.get(tokSize - 2).getText().equals(";")) {
                        break;
                    }
                    
                    System.out.printf(" %5d> ", ++lineNum);
                    errorListener.clear();
                } while (true);
                
            } catch (EOFException e) {
                System.out.println(" -- EOF DETECTED --");
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                inputs.setLength(0);
                
                if (tokens != null && !checkAndPrintError("lex error (NO ACTION WAS PERFORMED)")) {
                    ParseTree tree = parse(tokens);
                    
                    if (!checkAndPrintError("parse error (NO ACTION WAS PERFORMED)")) {
                        List<SimpleSQLOperation> operations = doCompile(tree);
                        checkAndPrintError("compile error (NO ACTION AFTER THE 1st ERROR WAS PERFORMED)");
                        
                        for (SimpleSQLOperation op : operations) {
                            op.execute(errorListener, tableList);
                            
                            if (checkAndPrintError("(NO ACTION AFTER THIS ERROR WAS PERFORMED)")) {
                                break;
                            }
                        }
                        
                    }
                }
                
                System.err.println();
            }
            
        }
    }
    
    private void save() throws IOException {
        try {
            for (Table table : tableList.values()) {
                table.db.commit();
                table.db.close();
            }
            
            File p = tableFile.getParentFile();
            if (p != null) {
                p.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(tableFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            
            Static.writeTables(tableList, oos);
            oos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    List<SimpleSQLOperation> doCompile(ParseTree tree) {
        SimpleSQLVisitor visitor = new SimpleSQLVisitorImpl(errorListener);
        return (List<SimpleSQLOperation>) visitor.visit(tree);
    }
    
    CommonTokenStream lex(String input) {
        SimpleSQLLexer lexer = new SimpleSQLLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        tokens.fill();
        
        return tokens;
    }
    
    ParseTree parse(TokenStream tokens) {
        SimpleSQLParser parser = new SimpleSQLParser(tokens);
        
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        return parser.statementList();
    }
    
    boolean checkAndPrintError(String title) {
        Static.out.flush();
        if (errorListener.hasError()) {
            System.err.println();
            System.err.println(" -- " + title + " --");
            errorListener.printErrorMsg(System.err);
            errorListener.clear();
            return true;
        }
        return false;
    }
    
    void readLine(StringBuilder str) throws IOException {
        if (console == null) {
            String line = reader.readLine();
            if (line == null) throw new EOFException();
            str.append(line).append('\n');
        } else {
            int ch;
            while( (ch = System.in.read()) != -1 && ch != '\n' ) {
                str.append((char) ch);
            }
            str.append('\n');
            if (ch == -1) {
                throw new EOFException();
            }
        }
    }
    
    private String readFromFile(String filename) throws IOException {
        StringBuilder str = new StringBuilder();
        BufferedReader r = new BufferedReader(new FileReader(filename));
        char[] b = new char[1024];
        int numRead = 0;
        while ((numRead = r.read(b)) != -1) {
            str.append(b, 0, numRead);
        }
        
        r.close();
        return str.toString();
    }
    
    public static void main(String[] args) {
        console = System.console();
        
        if (console == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        
        new SimpleDBMain();
        
    }
}
