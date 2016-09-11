package com.gmail.chh9513136.simpledb.compiler;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class SilentErrorListener implements ANTLRErrorListener {

    private String inputLines[];
    private StringBuilder errorMsg = new StringBuilder();
    private static final Map<Integer, String> ruleMap;
    private static final Set<Integer> skipList;
    private boolean hasError = false;

    static {
        ruleMap = new HashMap<>();
        ruleMap.put(SimpleSQLParser.RULE_columnList, "column list separated by \',\'");
        ruleMap.put(SimpleSQLParser.RULE_columnName, "column name");
        ruleMap.put(SimpleSQLParser.RULE_datatype, "datatype (either INT or VARCHAR)");
        ruleMap.put(SimpleSQLParser.RULE_expression, "boolean expression");
        ruleMap.put(SimpleSQLParser.RULE_statement, "SQL statement");
        ruleMap.put(SimpleSQLParser.RULE_statementList, "SQL statements separated by \';\'");
        ruleMap.put(SimpleSQLParser.RULE_tableName, "table name");
        ruleMap.put(SimpleSQLParser.RULE_typeLength, "type length (a non-negative number)");
        ruleMap.put(SimpleSQLParser.RULE_valueList, "value list separated by \',\'");
        ruleMap.put(SimpleSQLParser.RULE_literalValue, "literal value");
        
        
        ruleMap.put(SimpleSQLParser.RULE_selectStmt, "aggregate function OR column names");
        ruleMap.put(SimpleSQLParser.RULE_columnSpec, "column name");
        ruleMap.put(SimpleSQLParser.RULE_selectList, "column list separated by \',\'");
        ruleMap.put(SimpleSQLParser.RULE_tableSpecList, "table list separated by \',\'");
        //ruleMap.put(SimpleSQLParser.RULE_compare, "compare operator");
        ruleMap.put(SimpleSQLParser.RULE_tableSpec, "table name");
        ruleMap.put(SimpleSQLParser.RULE_term, "int value, string value or column name");
        ruleMap.put(SimpleSQLParser.RULE_condition, "boolean expression");
        ruleMap.put(SimpleSQLParser.RULE_condRHS, "compare expression (compare operator + operand)");

        skipList = new HashSet<>();
        skipList.add(SimpleSQLParser.RULE_condition);
        //skipList.add(SimpleSQLParser.RULE_condRHS);
        //skipList.add(SimpleSQLParser.RULE_term);
        skipList.add(SimpleSQLParser.RULE_andCondition);
    }

    @Override
    public void syntaxError(Recognizer recognizer, Object offendingSymbol,
            int line, int charPositionInLine, String msg, RecognitionException e) {
        
        if (offendingSymbol == null) return;
        
        Token t = (Token) offendingSymbol;
        
        if (recognizer instanceof Parser) {
            Parser p = (Parser) recognizer;
            ParserRuleContext ctx = p.getRuleContext();
            int ruleId = 0;
            boolean skip = true;
            
            ruleId = ctx.getRuleIndex();
            skip = skipList.contains(ruleId);
            while (ctx != null && skip == true) {
                ruleId = ctx.getRuleIndex();
                skip = skipList.contains(ruleId);
                ctx = ctx.getParent();
            }

            String replacement = (ctx == null) ? null : ruleMap.get(ruleId);

            if (replacement != null) {
                if (msg.startsWith("missing")) {
                    msg = "missing " + replacement + " before " + recognizer.getTokenErrorDisplay(t);
                } else if (msg.startsWith("mismatched input")) {
                    msg = "mismatched input " + p.getTokenErrorDisplay(t) + ", expecting " + replacement;
                } else if (msg.startsWith("extraneous input")) {
                    msg = "unexpected " + p.getTokenErrorDisplay(t) + ", expecting " + replacement;
                } else if (msg.startsWith("no viable")) {
                    msg = "expecting " + replacement + " at " + p.getTokenErrorDisplay(t);
                }
            }
        }
        errorMsg.append("Line ").append(line).append(":").append(charPositionInLine).append(" : ").append(msg);
        underlineError(t, line, charPositionInLine, true);
        errorMsg.append('\n');
        hasError = true;
    }
    
    public void init(String input) {
        inputLines = input.split("\n");
    }
    
    public void appendLine(String msg, Token ... tokens) {
        
        if (tokens.length == 0) {
            errorMsg.append(msg);
        } else {
            int line = tokens[0].getLine();
            int charPositionInLine = tokens[0].getCharPositionInLine();
            errorMsg.append("Line ").append(line).append(":").append(charPositionInLine).append(" : ").append(msg);
            
            for (int i = 0; i < tokens.length; i++) {
                boolean newLine = true;
                if (i != 0) {
                    newLine = (i==0)||(tokens[i].getLine() != tokens[i-1].getLine());
                    
                    if (newLine)
                        charPositionInLine = tokens[i].getCharPositionInLine();
                    else
                        charPositionInLine = tokens[i].getCharPositionInLine() - charPositionInLine
                        + tokens[i-1].getStartIndex() - tokens[i-1].getStopIndex() - 1;
                }
                
                underlineError(tokens[i], tokens[i].getLine(), charPositionInLine, newLine);
            }
        }
        
        errorMsg.append('\n');
        hasError = true;
    }

    private void underlineError(Token offendingToken, int line, int charPositionInLine, boolean isNewLine) {
        String errorLine = inputLines[line - 1];
        
        if (isNewLine) {
            errorMsg.append('\n').append(String.format(" %5d> ", line)).append(errorLine).append('\n').append("        ");
        }
        
        for (int i = 0; i < charPositionInLine; i++) {
            if (errorLine.charAt(i) == '\t')
                errorMsg.append('\t');
            else
                errorMsg.append(' ');
        }
        
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if (start >= 0 && stop >= 0) {
            for (int i = start; i <= stop; i++)
                errorMsg.append('^');
        }
        
    }

    public void clear() {
        errorMsg.setLength(0);
        hasError = false;
    }

    public void printErrorMsg(PrintStream stream) {
        stream.print(errorMsg.toString());
    }

    public boolean hasError() {
        return hasError;
    }

}
