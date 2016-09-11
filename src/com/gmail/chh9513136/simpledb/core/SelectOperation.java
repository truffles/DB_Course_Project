package com.gmail.chh9513136.simpledb.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mapdb.BTreeMap;
import org.mapdb.Fun;

import com.gmail.chh9513136.simpledb.Static;
import com.gmail.chh9513136.simpledb.compiler.SilentErrorListener;
import com.gmail.chh9513136.simpledb.core.Attr.NullAttr;
import com.gmail.chh9513136.simpledb.expr.AndExpr;
import com.gmail.chh9513136.simpledb.expr.ColumnSpec;
import com.gmail.chh9513136.simpledb.expr.CompareExpr;
import com.gmail.chh9513136.simpledb.expr.Expr;
import com.gmail.chh9513136.simpledb.expr.LiteralExpr;
import com.gmail.chh9513136.simpledb.expr.OrExpr;
import com.gmail.chh9513136.simpledb.expr.TableSpec;

public class SelectOperation implements SimpleSQLOperation {
    private final static int nothing = 0;
    private final static int and = 1;
    private final static int or = 2;

    public final List<ColumnSpec> selectList; // either this
    public final Aggregate aggregate; // or this
    public final List<TableSpec> fromList;
    public final Expr whereExpr;
    public static final int table_limit = 6;
    public boolean selectIsStar, aggrIsStar;
    private int op_type;
    private int R_setSize, L_setSize;
    private int and_res_setSize, or_res_setSize;
    private int aggr_count = 0;;

    private CompareExpression leftComExp; /* A=B */
    private CompareExpression rightComExp;

    public SelectOperation(List<ColumnSpec> selectList, Aggregate aggregate, List<TableSpec> fromList, Expr whereExpr) {
        this.selectList = selectList;
        this.aggregate = aggregate;
        this.fromList = fromList;
        this.whereExpr = whereExpr;
    }

    @Override
    public Object execute(SilentErrorListener errListener, Map<String, Table> tableList) {
        Map<String, String> aliasTable = new HashMap<String, String>();
        List<Tuple> left = null;
        List<Tuple> right = null;
        List<String> needed_tableL = new ArrayList<String>();
        List<String> needed_tableR = new ArrayList<String>();
        List<ColumnSpec> specList = null;
        long time = System.currentTimeMillis();

        try {
            for (TableSpec table : fromList) { // tableName ,alias are in
                                               // lowercase and are not case
                                               // sensitive.
                Table tableInFromList = tableList.get(table.tableName);
                if (tableInFromList == null)
                    throw new Exception("Error: No such a table \"" + table.tableName + "\"");
                aliasTable.put(table.tableName, table.tableName);
                if (table.alias != null)
                    aliasTable.put(table.alias, table.tableName);
            }

            if (selectList != null) {
                checkSelectList(aliasTable, tableList);
                specList = FindSpecList(tableList);
            } else if (aggregate != null) {
                // printAggr();
                checkAggrList(aliasTable, tableList);
            } else { // *
                selectIsStar = true;
            }

            if (whereExpr instanceof OrExpr) {
                op_type = or;
            } else if (whereExpr instanceof AndExpr) {
                op_type = and;
            } else {
                op_type = nothing;
            }
            CompareExpression temp;
            if (whereExpr != null) {
                checkWhereExpr(whereExpr, aliasTable, tableList);
                if (op_type == nothing) {
                    left = whereNothingOp(specList, leftComExp, tableList, needed_tableL);
                } else {
                    if (op_type == and) {
                    	if ((leftComExp.left.type == CompareLiteral.COLDEF && leftComExp.right.type != CompareLiteral.COLDEF) || (leftComExp.right.type == CompareLiteral.COLDEF && leftComExp.left.type != CompareLiteral.COLDEF)) {
                            temp = leftComExp;
                            leftComExp = rightComExp;
                            rightComExp = temp;
                        }
                    	alterPosition(leftComExp);
                        alterPosition(rightComExp);
                        alterCmpExp(leftComExp, ((String[])rightComExp.left.getEle())[0]);
                        left = andOp(tableList, left, needed_tableL);
                        /*needed_tableL is already filled */
                    } else if (op_type == or) {
                    	if ((leftComExp.left.type == CompareLiteral.COLDEF && leftComExp.right.type != CompareLiteral.COLDEF) || (leftComExp.right.type == CompareLiteral.COLDEF && leftComExp.left.type != CompareLiteral.COLDEF)) {
                            temp = leftComExp;
                            leftComExp = rightComExp;
                            rightComExp = temp;
                        }
                        alterPosition(leftComExp);
                        alterPosition(rightComExp);
                        alterCmpExp(leftComExp, ((String[])rightComExp.left.getEle())[0]);
                        right = produceRelatedTupleList(rightComExp, tableList, needed_tableR, 1);
                        left = orOp(tableList, left, needed_tableL);
                    }
                }
            }

            /* print it or count the number*/
            if (whereExpr == null) {
                if (aggregate != null) {
                    aggr(tableList);
                } else{
                	printSpecList(specList);
                    selectFrom(tableList);
                }
            } else {
                if (aggregate != null) { /* aggregate functions */
                    if (op_type == nothing) {
                        if (aggregate.type == 0) {/* COUNT */
                            nothingCount(tableList, left, needed_tableL, true);
                        }

                        else if (aggregate.type == 1) {/* SUM */
                            nothingSum(tableList,left, needed_tableL, true);
                        }
                    } else if (op_type == and) {/* and, or */
                        if (aggregate.type == 0) {/* COUNT */
                            nothingCount(tableList, left, needed_tableL, true);
                        }

                        else if (aggregate.type == 1) {/* SUM */
                            nothingSum(tableList,left, needed_tableL, true);
                        }
                    } else if (op_type == or) {
                        if (aggregate.type == 0) {/* COUNT */
                            nothingCount(tableList, right, needed_tableR, false);
                            nothingCount(tableList, left, needed_tableL, true);

                        } else if (aggregate.type == 1) {/* SUM */
                            nothingSum(tableList,left, needed_tableL, false);
                            nothingSum(tableList,right, needed_tableR, true);
                        }
                    }
                } else {/* select functions */
                    printSpecList(specList);
                    if (op_type == nothing) {
                        if ((leftComExp.left.type == CompareLiteral.COLDEF && leftComExp.right.type != CompareLiteral.COLDEF) || (leftComExp.right.type == CompareLiteral.COLDEF && leftComExp.left.type != CompareLiteral.COLDEF)) {
                            printCorrectTuples(left, tableList, needed_tableL);
                        } else {
                            printCmpExpr2(specList, tableList.get(leftComExp.left.tableName), tableList.get(leftComExp.right.tableName), left);
                        }
                    } else {
                        if (op_type == and) {
                            if (and_res_setSize == 1)
                                printCorrectTuples(left, tableList, needed_tableL);
                            else if (and_res_setSize == 2) {
                                printCmpExpr2(specList, tableList.get(needed_tableL.get(0)), tableList.get(needed_tableL.get(1)), left);
                            }
                        } else if (op_type == or) {
                            printCorrectTuples(right, tableList, needed_tableR);
                            if (or_res_setSize == 1)
                                printCorrectTuples(left, tableList, needed_tableL);
                            else if (or_res_setSize == 2) {
                                printCmpExpr2(specList, tableList.get(needed_tableL.get(0)), tableList.get(needed_tableL.get(1)), left);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            errListener.appendLine(e.getMessage());
        } finally {
            Static.out.println("Time: " + (System.currentTimeMillis() - time) + " ms");
        }
        
        return null;
    }

    /* produce the tupleList result of orOp */
    private List<Tuple> orOp(Map<String, Table> tableList, List<Tuple> left, List<String> needed_tableL) {
        String com_attr = null;
        boolean first;
        int len, i;
        List<String> needed_tableName = new ArrayList<String>();
        left = produceRelatedTupleList(leftComExp, tableList, needed_tableName, 0);
        com_attr = ((String[]) rightComExp.left.getEle())[1];
        first = (needed_tableName.get(0).equals(((String[]) rightComExp.left.getEle())[0])) ? true : false;
        len = left.size();
        List<Tuple> res = new ArrayList<Tuple>();
        boolean needed_in = false;
        if (L_setSize == 2) { /* a.id = b.id or a.id = 5 */
        	Table t0 = tableList.get(needed_tableName.get(0));
        	Table t1 = tableList.get(needed_tableName.get(1));
            for (i = 0; i < len; i += 2) {
                if (first) {
                    // Static.out.println("first");
                    if (!cmpAnd(t0, left.get(i), com_attr)) {
                        res.add(left.get(i));
                        res.add(left.get(i + 1));
                        
						if(!needed_in){
                        	needed_tableL.add(needed_tableName.get(i % L_setSize));
                        	needed_tableL.add(needed_tableName.get((i + 1)% L_setSize));
                        	needed_in = true;
                        }
                    }
                } else {
                    // Static.out.println("second");
                    if (!cmpAnd(t1, left.get(i + 1), com_attr)) {
                        res.add(left.get(i));
                        res.add(left.get(i + 1));
                        if(!needed_in){
                        	needed_tableL.add(needed_tableName.get(i% L_setSize));
                        	needed_tableL.add(needed_tableName.get((i + 1)% L_setSize));
                        	needed_in = true;
                        }
                    }
                }
            }
            or_res_setSize = 2;
        } else if (L_setSize == 1) {
        	Table t0 = tableList.get(needed_tableName.get(0));
            for (i = 0; i < len; i++) {
                if (!cmpAnd(t0, left.get(i), com_attr)) {
                    res.add(left.get(i));
                    if(!needed_in){
	                    needed_tableL.add(needed_tableName.get(i% L_setSize));
	                    needed_in = true;
                    }
                }
            }
            or_res_setSize = 1;
        }
        return res;
    }

    /* produce result of andOp tupleList */
    private List<Tuple> andOp(Map<String, Table> tableList, List<Tuple> left, List<String> needed_tableL) {
        Iterable<Tuple> right_res = null;
        boolean needed_in = false;
        List<Tuple> res = new ArrayList<Tuple>();
       
        Table t = tableList.get(((String[])rightComExp.left.getEle())[0]);
        ColumnDef  ColDef = t.columnDefMap.get(((String[])rightComExp.left.getEle())[1]);
        if(rightComExp.right.type == CompareLiteral.INT){
        	
        	switch (rightComExp.op) {
	            case CompareExpr.GT: {
	            	IntAttr attr = new IntAttr((int) rightComExp.right.getEle());
	            	IntAttr max = new IntAttr(Integer.MAX_VALUE);
	            	attr.val++;
	            	right_res = t.getRangeFast(ColDef, attr, true, max, true);
	                if(right_res==null){
	                	right_res = t.getRangeSlow(ColDef, attr, true, max, true);
	                }
	            break;
	            }
	            case CompareExpr.LT: {
	            	IntAttr attr = new IntAttr((int) rightComExp.right.getEle());
	            	IntAttr min = new IntAttr(Integer.MIN_VALUE);
	            	attr.val--;
	            	right_res = t.getRangeFast(ColDef, min, true, attr, true);
	            	if (right_res == null) {
	            		right_res = t.getRangeSlow(ColDef, min, true, attr, true);
	            	}
	            break;
	            }
	            case CompareExpr.EQ: {
	            	IntAttr attr = new IntAttr((int)rightComExp.right.getEle());
	            	right_res = t.getEquals(ColDef,attr);
	            break;
	            }
	            case CompareExpr.NEQ: {
	            	Attr attr1 = new IntAttr((int)rightComExp.right.getEle());
	            	right_res = t.getNotEquals(ColDef,attr1);
                break;
	            }
            }
        }
        else if(rightComExp.right.type == CompareLiteral.STR){
        	switch (rightComExp.op) {
		        case CompareExpr.EQ:
		        	Attr attr = new VarcharAttr((String)rightComExp.right.getEle());
		        	right_res = t.getEquals(ColDef,attr);
		        break;
		        case CompareExpr.NEQ:
		        	Attr attr1 = new VarcharAttr((String)rightComExp.right.getEle());
		        	right_res = t.getNotEquals(ColDef,attr1);
		        break;
        	}
        }
        
        Collection<Tuple> right_col;
        
        if(!(right_res instanceof Collection)){
            right_col = Table.packAsSet(right_res);
        } else {
            right_col = (Collection<Tuple>) right_res;
        }
        
        if(!right_col.isEmpty()){
        	
        	if(leftComExp.right.type == CompareLiteral.COLDEF){
	        	Table t_left = tableList.get(((String[])leftComExp.right.getEle())[0]);
	        	if(t_left.tableName.equals(t.tableName)){
		        	Map<Comparable, Tuple> map;
		        	if (t_left.primaryKey.indexing == ColumnDef.HASH_IDX){
		        		map = t_left.primaryKey.asHTreeMap();
		        	}
		        	else{
		        		map = t_left.primaryKey.asBTreeMap();
		        	}
		        	String[] LColDef = (String[]) (leftComExp.left.getEle());
		            String[] RColDef = (String[]) (leftComExp.right.getEle());

		        	for(Tuple tuple_right: right_col){
		        		for(Tuple tuple2: map.values()){
		        			if(cmpAnd(leftComExp, t, tuple_right, t_left, tuple2, LColDef, RColDef)){
		        				and_res_setSize = 2;
		        				res.add(tuple_right);
		        				res.add(tuple2);
		        				if(!needed_in){
		    	                    needed_tableL.add(t.tableName.toLowerCase());
		    	                    needed_tableL.add(t_left.tableName.toLowerCase());
		    	                    needed_in = true;
		                        }
		        			}
		        		}
		        	}
	        	}
	        	else{
	        		String[] LColDef = (String[]) (leftComExp.left.getEle());
		            String[] RColDef = (String[]) (leftComExp.right.getEle());
		            ColumnDef LCol = tableList.get(LColDef[0]).columnDefMap.get(LColDef[1]);
		            ColumnDef RCol = tableList.get(RColDef[0]).columnDefMap.get(RColDef[1]);
		            Map<Comparable, List<Tuple>> inter1 = new HashMap<>();
		            Map<Comparable, List<Tuple>> inter2 = new HashMap<>();
		            // inter1
		            if(LCol.isPrimaryKey){
		            	if (LCol.indexing == ColumnDef.HASH_IDX) {
		            		for(Entry<Comparable, Tuple> ent : LCol.asHTreeMap().entrySet()) {
		            			List<Tuple> list = new ArrayList<>();
		            			list.add(ent.getValue());
		            			inter1.put(ent.getKey(), list);
		            		}
		            	} else {
		            		for(Entry<Comparable, Tuple> ent : LCol.asBTreeMap().entrySet()) {
		            			List<Tuple> list = new ArrayList<>();
		            			list.add(ent.getValue());
		            			inter1.put(ent.getKey(), list);
		            		}
		            	}
		            } else {
		            	if (LCol.indexing == ColumnDef.HASH_IDX) {
		            		for (Fun.Tuple2<Comparable, Tuple> ent : LCol.asSet()) {
		            			List<Tuple> list = inter1.get(ent.a);
		            			if (list == null) {
		            				list = new ArrayList<>();
		            				inter1.put(ent.a, list);
		            			}
		            			list.add(ent.b);
		            		}
		            	} else {
		            		for (Fun.Tuple2<Comparable, Tuple> ent : LCol.asNavigableSet()) {
		            			List<Tuple> list = inter1.get(ent.a);
		            			if (list == null) {
		            				list = new ArrayList<>();
		            				inter1.put(ent.a, list);
		            			}
		            			list.add(ent.b);
		            		}
		            	}
		            }
		            
		            // inter2
		            if(RCol.isPrimaryKey){
		            	if (RCol.indexing == ColumnDef.HASH_IDX) {
		            		for(Entry<Comparable, Tuple> ent : RCol.asHTreeMap().entrySet()) {
		            			List<Tuple> list = new ArrayList<>();
		            			list.add(ent.getValue());
		            			inter2.put(ent.getKey(), list);
		            		}
		            	} else {
		            		for(Entry<Comparable, Tuple> ent : RCol.asBTreeMap().entrySet()) {
		            			List<Tuple> list = new ArrayList<>();
		            			list.add(ent.getValue());
		            			inter2.put(ent.getKey(), list);
		            		}
		            	}
		            } else {
		            	if (RCol.indexing == ColumnDef.HASH_IDX) {
		            		for (Fun.Tuple2<Comparable, Tuple> ent : RCol.asSet()) {
		            			List<Tuple> list = inter2.get(ent.a);
		            			if (list == null) {
		            				list = new ArrayList<>();
		            				inter2.put(ent.a, list);
		            			}
		            			list.add(ent.b);
		            		}
		            	} else {
		            		for (Fun.Tuple2<Comparable, Tuple> ent : RCol.asNavigableSet()) {
		            			List<Tuple> list = inter2.get(ent.a);
		            			if (list == null) {
		            				list = new ArrayList<>();
		            				inter2.put(ent.a, list);
		            			}
		            			list.add(ent.b);
		            		}
		            	}
		            }
	        		
		            inter2.keySet().remove(NullAttr.INSTANCE);
	        		inter1.keySet().retainAll(inter2.keySet());
	        		inter2.keySet().retainAll(inter1.keySet());
	        		
        			Collection<Tuple> rs = right_col;
        			for(Entry<Comparable, List<Tuple>> entry: inter1.entrySet()){
	        			Comparable value = entry.getKey();
	        			List<Tuple> tuple_list = entry.getValue();
	        			Iterator<Tuple> iter = tuple_list.iterator();
		        		while(iter.hasNext()){
		        			Tuple tuple1 = iter.next();
		        			if(!rs.contains(tuple1)){
		        				iter.remove();
		        			}
		        		}
		        	}
	        		
	        		for(Entry<Comparable, List<Tuple>> entry: inter1.entrySet()){
	        			Comparable value = entry.getKey();
	        			List<Tuple> tuple_list = entry.getValue();
		        		for(Tuple tuple1 : tuple_list){
		        			for(Tuple tuple2 : inter2.get(value)) {
		        				and_res_setSize = 2;
		        				res.add(tuple1);
		        				res.add(tuple2);
		        				if(!needed_in){
		    	                    needed_tableL.add(tableList.get(LColDef[0]).tableName.toLowerCase());
		    	                    needed_tableL.add(tableList.get(RColDef[0]).tableName.toLowerCase());
		    	                    needed_in = true;
		                        }
		        			}
		        		}
		        	}
	        	}
        	}
        	else if(leftComExp.right.type == CompareLiteral.INT){
        		and_res_setSize = 1;
        		String[] LColDef = (String[]) (leftComExp.left.getEle());
        		for(Tuple tuple_right: right_col){
        			if(cmpAnd(leftComExp, t, tuple_right, (int)leftComExp.right.getEle(),LColDef)){
        				res.add(tuple_right);
        				if(!needed_in){
    	                    needed_tableL.add(t.tableName.toLowerCase());
    	                    needed_in = true;
                        }
        			}
        		}
        	}
        	else if(leftComExp.right.type == CompareLiteral.STR){
        		and_res_setSize = 1;
        		String[] LColDef = (String[]) (leftComExp.left.getEle());
        		for(Tuple tuple_right: right_col){
        			if(cmpAnd(leftComExp, t, tuple_right, (String)leftComExp.right.getEle(), LColDef)){
        				res.add(tuple_right);
        				if(!needed_in){
    	                    needed_tableL.add(t.tableName.toLowerCase());
    	                    needed_in = true;
                        }
        			}
        		}
        	}
        }
        else{
        	//res = produceRelatedTupleList(leftComExp, tableList, needed_tableL, 0);
        	res = null;
        }
        return res;
    }
    
    private boolean cmpAnd(CompareExpression leftComExp, Table LTable, Tuple tuple_left, String str, String[] LColDef) {
    	switch (leftComExp.op) {
            case CompareExpr.EQ:
            	if(((String)getAttrValue(LTable, tuple_left ,LColDef[1])).equals(str)==true){
            		return true;
            	}
            break;
            case CompareExpr.NEQ:
            	if(((String)getAttrValue(LTable, tuple_left ,LColDef[1])).equals(str)==false){
            		return true;
            	}
            break;
        }
		return false;
	}

	private boolean cmpAnd(CompareExpression leftComExp, Table LTable, Tuple tuple_left, int num, String[] LColDef) {
		
    	switch (leftComExp.op) {
            case CompareExpr.GT:
            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) > num){
            		return true;
            	}
            break;
            case CompareExpr.LT:
            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) < num){
            		return true;
            	}
            break;
            case CompareExpr.EQ:
            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) == num){
            		return true;
            	}
            break;
            case CompareExpr.NEQ:
            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) != num){
            		return true;
            	}
            break;
        }
		return false;
	}

	private boolean cmpAnd(CompareExpression leftComExp, Table LTable, Tuple tuple_left, Table RTable, Tuple tuple_right, String[] LColDef, String[] RColDef) {

        ColumnDef Ld = LTable.columnDefMap.get(LColDef[1]);
        if (Ld.datatype instanceof Datatype.IntDatatype){
        	switch (leftComExp.op) {
	            case CompareExpr.GT:
	            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) > ((int)getAttrValue(RTable, tuple_right ,RColDef[1]))){
	            		return true;
	            	}
	            break;
	            case CompareExpr.LT:
	            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) < ((int)getAttrValue(RTable, tuple_right ,RColDef[1]))){
	            		return true;
	            	}
	            break;
	            case CompareExpr.EQ:
	            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) == ((int)getAttrValue(RTable, tuple_right ,RColDef[1]))){
	            		return true;
	            	}
	            break;
	            case CompareExpr.NEQ:
	            	if((int)getAttrValue(LTable, tuple_left ,LColDef[1]) != ((int)getAttrValue(RTable, tuple_right ,RColDef[1]))){
	            		return true;
	            	}
                break;
            }
        }
        else{
        	switch (leftComExp.op) {
			    case CompareExpr.EQ:
			    	if(((String)getAttrValue(LTable, tuple_left ,LColDef[1])).equals(((String)getAttrValue(RTable, tuple_right ,RColDef[1])))==true){
			    		return true;
			    	}
			    break;
			    case CompareExpr.NEQ:
			    	if(((String)getAttrValue(LTable, tuple_left ,LColDef[1])).equals(((String)getAttrValue(RTable, tuple_right ,RColDef[1])))==false){
			    		return true;
			    	}
			    break;
        	}
        }
		return false;
	}

	/*the same table name in left side*/
    private void alterCmpExp(CompareExpression leftComExp, String tableName){
    	if(leftComExp.right.type==CompareLiteral.COLDEF){
	    	if(((String[])leftComExp.right.getEle())[0].equals(tableName)){
	    		CompareLiteral temp = leftComExp.right;
	    		leftComExp.right = leftComExp.left;
	    		leftComExp.left = temp;
	    		if(leftComExp.op == CompareExpr.GT)
	    			leftComExp.op = CompareExpr.LT;
	    		else if(leftComExp.op == CompareExpr.LT){
	    			leftComExp.op = CompareExpr.GT;
	    		}
	    	}
    	}
    }
    
    private boolean cmpAnd(Table t0, Tuple t, String be_compare) {
        // Static.out.println("comparing "+rightComExp.right.type);
        if (rightComExp.right.type == CompareLiteral.INT) {
            switch (rightComExp.op) {
            case CompareExpr.GT:
                if ((Integer) getAttrValue(t0, t, be_compare) > (int) rightComExp.right.getEle()) {
                    return true;
                }
                break;
            case CompareExpr.LT:
                if ((Integer) getAttrValue(t0, t, be_compare) < (int) rightComExp.right.getEle()) {
                    return true;
                }
                break;
            case CompareExpr.EQ:
                if ((Integer) getAttrValue(t0, t, be_compare) == (int) rightComExp.right.getEle()) {
                    return true;
                }
                break;
            case CompareExpr.NEQ:
                if ((Integer) getAttrValue(t0, t, be_compare) != (int) rightComExp.right.getEle()) {
                    return true;
                }
                break;
            }
        } else if (rightComExp.right.type == CompareLiteral.STR) {
            // Static.out.println(be_compare);
            switch (rightComExp.op) {
            case CompareExpr.EQ:
                if (((String) getAttrValue(t0, t, be_compare)).equals((String) rightComExp.right.getEle()) == true) {
                    return true;
                }
                break;
            case CompareExpr.NEQ:
                if (((String) getAttrValue(t0, t, be_compare)).equals((String) rightComExp.right.getEle()) == false) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    Object getAttrValue(Table table, Tuple t, String attrneme){
    	int attr_index = table.columnDefMap.get(attrneme).index;
    	Attr attr = t.getAttr(attr_index);
    	return attr.getValue();
    }
    
    private void nothingSum(Map<String, Table> tableList, List<Tuple> left, List<String> needed_tableL, boolean show) {
        int i, len;
        boolean first = false;
        if(left==null){
        	if (show) {
                Static.out.println(aggr_count);
            }
        	return;
        }
        if ((leftComExp.left.type == CompareLiteral.COLDEF && leftComExp.right.type != CompareLiteral.COLDEF) || (leftComExp.right.type == CompareLiteral.COLDEF && leftComExp.left.type != CompareLiteral.COLDEF)) {
            if (aggregate.column.columnName == null) {
                if (show)
                    Static.out.println(left.size());
            } else {
            	Table t0 = tableList.get(needed_tableL.get(0));
                for (Tuple t : left) {
                    if (getAttrValue(t0, t, aggregate.column.columnName) instanceof Integer) {
                        aggr_count += (Integer) getAttrValue(t0, t, aggregate.column.columnName);
                    } else {
                        throw new RuntimeException("Error: varchar can't be summed");
                    }
                }
                if (show)
                    Static.out.println(aggr_count);
            }
        } else {
            if (aggregate.column.columnName == null) {
                throw new RuntimeException("Error: sum can't be wildcard");
            } else {
                first = (needed_tableL.get(0).equals(aggregate.column.tableName)) ? true : false;
                len = left.size();
                Table t0 = tableList.get(needed_tableL.get(0));
                Table t1 = tableList.get(needed_tableL.get(1));
                for (i = 0; i < len; i += 2) {
                    if (first) {
                        if (getAttrValue(t0, left.get(i), aggregate.column.columnName) instanceof Integer) {
                            aggr_count += (Integer) getAttrValue(t0, left.get(i), aggregate.column.columnName);
                        } else {
                            throw new RuntimeException("Error: varchar can't be summed");
                        }
                    } else {
                        if (getAttrValue(t1, left.get(i+1), aggregate.column.columnName) instanceof Integer) {
                            aggr_count += (Integer) getAttrValue(t1, left.get(i+1), aggregate.column.columnName);
                        } else {
                            throw new RuntimeException("Error: varchar can't be summed");
                        }
                    }
                }
                if (show)
                    Static.out.println(aggr_count);
            }
        }
    }

    private void nothingCount(Map<String, Table> tableList, List<Tuple> left, List<String> needed_tableL, boolean show) {
        int i, len;
        boolean first;
        if(left==null){
        	if (show) {
                Static.out.println(aggr_count);
            }
        	return;
        }
        if ((leftComExp.left.type == CompareLiteral.COLDEF && leftComExp.right.type != CompareLiteral.COLDEF) || (leftComExp.right.type == CompareLiteral.COLDEF && leftComExp.left.type != CompareLiteral.COLDEF)) {
            if (aggregate.column.columnName == null) {
                aggr_count += left.size();

            } else {
            	Table t0 = tableList.get(needed_tableL.get(0));
                for (Tuple t : left) {
                    if (getAttrValue(t0, t, aggregate.column.columnName) != null) {
                        aggr_count++;
                    }
                }
            }
        } else {
            len = left.size();
            if (aggregate.column.columnName == null) {
                aggr_count += len / 2;
            } else {
                first = (needed_tableL.get(0).equals(aggregate.column.tableName)) ? true : false;
                Table t0 = tableList.get(needed_tableL.get(0));
                Table t1 = tableList.get(needed_tableL.get(1));
                for (i = 0; i < len; i += 2) {
                    if (first) {
                        if (getAttrValue(t0, left.get(i), aggregate.column.columnName) != null) {
                            aggr_count++;
                        }
                    } else {
                        if (getAttrValue(t1, left.get(i+1), aggregate.column.columnName) != null) {
                            aggr_count++;
                        }
                    }
                }

            }
        }
        
        if (show) {
            Static.out.println(aggr_count);
        }
    }

    private List<Tuple> whereNothingOp(List<ColumnSpec> specList, CompareExpression expr, Map<String, Table> tableList, List<String> needed_table) {
        List<Tuple> list;

        if ((expr.left.type == CompareLiteral.COLDEF && expr.right.type != CompareLiteral.COLDEF) || (expr.right.type == CompareLiteral.COLDEF && expr.left.type != CompareLiteral.COLDEF)) {
            alterPosition(expr);
            list = (ArrayList<Tuple>) opCmpExpr(expr, tableList.get(expr.left.tableName), needed_table);
        } else {
            list = (ArrayList<Tuple>) opCmpExpr(expr, tableList.get(expr.left.tableName), tableList.get(expr.right.tableName), tableList, needed_table);
        }
        return list;
    }

    private List<Tuple> produceRelatedTupleList(CompareExpression expr, Map<String, Table> tableList, List<String> needed_table, int flag) {
        List<Tuple> list;
        if ((expr.left.type == CompareLiteral.COLDEF && expr.right.type != CompareLiteral.COLDEF) || (expr.right.type == CompareLiteral.COLDEF && expr.left.type != CompareLiteral.COLDEF)) {
            alterPosition(expr);
            list = (ArrayList<Tuple>) opCmpExpr(expr, tableList.get(expr.left.tableName), needed_table);
            if (flag == 0)
                L_setSize = 1;
            else
                R_setSize = 1;
        } else {
            list = (ArrayList<Tuple>) opCmpExpr(expr, tableList.get(expr.left.tableName), tableList.get(expr.right.tableName), tableList, needed_table);
            if (flag == 0)
                L_setSize = 2;
            else
                R_setSize = 2;
        }
        return list;
    }

    // a.name = b.name
    private List<Tuple> opCmpExpr(CompareExpression Expr, Table LTable, Table RTable, Map<String, Table> tableList, List<String> needed_table) {
        String[] LColDef = (String[]) (Expr.left.getEle());
        String[] RColDef = (String[]) (Expr.right.getEle());
        ArrayList<Tuple> needed = new ArrayList<Tuple>();

        int LattrIndex = LTable.columnDefList.indexOf(LTable.columnDefMap.get(LColDef[1]));
        int RattrIndex = RTable.columnDefList.indexOf(RTable.columnDefMap.get(RColDef[1]));
        boolean needed_in = false;
        boolean isStr = LTable.columnDefMap.get(LColDef[1]).datatype instanceof Datatype.VarcharDatatype;
        BTreeMap<Comparable, Tuple> Lmap = LTable.primaryKey.asBTreeMap();
        BTreeMap<Comparable, Tuple> Rmap = RTable.primaryKey.asBTreeMap();
        switch (Expr.op) {
        case CompareExpr.GT:
            for (Tuple tuple : Lmap.values()) {
                for (Tuple tuple1 : Rmap.values()) {
                    if (((IntAttr) tuple.getAttr(LattrIndex)).val > ((IntAttr) tuple1.getAttr(RattrIndex)).val) {

                        needed.add(tuple);
                        needed.add(tuple1);
                        if(!needed_in){
                        	needed_table.add(LTable.tableName.toLowerCase());
                        	needed_table.add(RTable.tableName.toLowerCase());
                        	needed_in = true;
                        }
                    }
                }
            }
            break;
        case CompareExpr.LT:
            for (Tuple tuple : Lmap.values()) {
                for (Tuple tuple1 : Rmap.values()) {
                    if (((IntAttr) tuple.getAttr(LattrIndex)).val < ((IntAttr) tuple1.getAttr(RattrIndex)).val) {

                        needed.add(tuple);
                        needed.add(tuple1);
                        if(!needed_in){
                        	needed_table.add(LTable.tableName.toLowerCase());
                        	needed_table.add(RTable.tableName.toLowerCase());
                        	needed_in = true;
                        }
                    }
                }
            }
            break;
        case CompareExpr.EQ:
            for (Tuple tuple : Lmap.values()) {
                for (Tuple tuple1 : Rmap.values()) {
                    if (!isStr) {
                        if (((IntAttr) tuple.getAttr(LattrIndex)).val == ((IntAttr) tuple1.getAttr(RattrIndex)).val) {
                            needed.add(tuple);
                            needed.add(tuple1);
                            if(!needed_in){
                            	needed_table.add(LTable.tableName.toLowerCase());
                            	needed_table.add(RTable.tableName.toLowerCase());
                            	needed_in = true;
                            }
                        }
                    } else {
                        if (((VarcharAttr) tuple.getAttr(LattrIndex)).val.equals(((VarcharAttr) tuple1.getAttr(RattrIndex)).val) == true) {
                            needed.add(tuple);
                            needed.add(tuple1);
                            if(!needed_in){
                            	needed_table.add(LTable.tableName.toLowerCase());
                            	needed_table.add(RTable.tableName.toLowerCase());
                            	needed_in = true;
                            }
                        }
                    }
                }
            }
            break;
        case CompareExpr.NEQ:
            for (Tuple tuple : Lmap.values()) {
                for (Tuple tuple1 : Rmap.values()) {
                    if (!isStr) {
                        if (((IntAttr) tuple.getAttr(LattrIndex)).val != ((IntAttr) tuple1.getAttr(RattrIndex)).val) {
                            needed.add(tuple);
                            needed.add(tuple1);
                            if(!needed_in){
                            	needed_table.add(LTable.tableName.toLowerCase());
                            	needed_table.add(RTable.tableName.toLowerCase());
                            	needed_in = true;
                            }
                        }
                    } else {
                        if (((VarcharAttr) tuple.getAttr(LattrIndex)).val.equals(((VarcharAttr) tuple1.getAttr(RattrIndex)).val) == false) {
                            needed.add(tuple);
                            needed.add(tuple1);
                            if(!needed_in){
                            	needed_table.add(LTable.tableName.toLowerCase());
                            	needed_table.add(RTable.tableName.toLowerCase());
                            	needed_in = true;
                            }
                        }
                    }
                }
            }
            break;
        }
        return needed;
    }

    // a.b = 1 or str
    private List<Tuple> opCmpExpr(CompareExpression Expr, Table t, List<String> needed_table) {
        String[] temp = (String[]) (Expr.left.getEle());
        boolean isStr = Expr.right.type == CompareLiteral.STR;
        ArrayList<Tuple> needed = new ArrayList<Tuple>();
        int attrList_index = t.columnDefList.indexOf(t.columnDefMap.get(temp[1]));
        Map<Comparable, Tuple> map;
        if (t.primaryKey.indexing == ColumnDef.HASH_IDX) {
            map = t.primaryKey.asHTreeMap();
        } else {
            map = t.primaryKey.asBTreeMap();
        }
        
        
        
        boolean needed_in = false;
        switch (Expr.op) {
        case CompareExpr.GT:
            for (Tuple tuple : map.values()) {
                if (((IntAttr) tuple.getAttr(attrList_index)).val > (int) Expr.right.getEle()) {
                    needed.add(tuple);
                    if(!needed_in){
                    	needed_table.add(t.tableName.toLowerCase());
                    	needed_in = true;
                    }
                }
            }
            break;
        case CompareExpr.LT:
            for (Tuple tuple : map.values()) {
                if (((IntAttr) tuple.getAttr(attrList_index)).val < (int) Expr.right.getEle()) {
                    needed.add(tuple);
                    if(!needed_in){
                    	needed_table.add(t.tableName.toLowerCase());
                    	needed_in = true;
                    }
                }
            }
            break;
        case CompareExpr.EQ:
            for (Tuple tuple : map.values()) {
                if (!isStr) {
                    /*
                    Static.out.println("EQ int");
                    Static.out.print("values " + ((IntAttr)tuple.getAttr(attrList_index)).val +" values "+(int)Expr.right.getEle());
                     */
                    if (((IntAttr) tuple.getAttr(attrList_index)).val == (int) Expr.right.getEle()) {
                        needed.add(tuple);
                        if(!needed_in){
                        	needed_table.add(t.tableName.toLowerCase());
                        	needed_in = true;
                        }
                    }
                } else {
                    if (((VarcharAttr) tuple.getAttr(attrList_index)).val.equals((String) Expr.right.getEle()) == true) {
                        needed.add(tuple);
                        if(!needed_in){
                        	needed_table.add(t.tableName.toLowerCase());
                        	needed_in = true;
                        }
                    }
                }
            }
            break;
        case CompareExpr.NEQ:
            for (Tuple tuple : map.values()) {
                if (!isStr) {
                    if (((IntAttr) tuple.getAttr(attrList_index)).val != (int) Expr.right.getEle()) {
                        needed.add(tuple);
                        if(!needed_in){
                        	needed_table.add(t.tableName.toLowerCase());
                        	needed_in = true;
                        }
                    }
                } else {
                    if (((VarcharAttr) tuple.getAttr(attrList_index)).val.equals((String) Expr.right.getEle()) == false) {
                        needed.add(tuple);
                        if(!needed_in){
                        	needed_table.add(t.tableName.toLowerCase());
                        	needed_in = true;
                        }
                    }
                }
            }
            break;
        }
        return needed;
    }

    private void printCorrectTuples(List<Tuple> left, Map<String, Table> tableList, List<String> needed_table) {
        int count = 0;
        int attrList_index;
        List<ColumnSpec> specList = FindSpecList(tableList);
        if(left == null){
        	Static.out.println("empty");
        	return;
        }
        for (Tuple tuple : left) {

            for (ColumnSpec col : specList) {
                if (col.tableName.equals(needed_table.get(0))) {
                    Table t = tableList.get(col.tableName);
                    attrList_index = t.columnDefList.indexOf(t.columnDefMap.get(col.columnName));
                    if (tuple.getAttr(attrList_index) instanceof IntAttr) {
                        Static.out.print("|" + ((IntAttr) tuple.getAttr(attrList_index)).val);
                    } else {
                        Static.out.print("|" + ((VarcharAttr) tuple.getAttr(attrList_index)).val);
                    }
                }
            }
            Static.out.println("|");
            count++;
        }
    }

    /**
     * Swap right.type should be int or str after that
     * @param cmpExp the expression
     */
    private void alterPosition(CompareExpression cmpExp) {
        CompareLiteral temp;
        if (cmpExp.right.type == CompareLiteral.COLDEF) {
            temp = cmpExp.right;
            cmpExp.right = cmpExp.left;
            cmpExp.left = temp;
            if(cmpExp.op == CompareExpr.GT){
            	cmpExp.op = CompareExpr.LT;
            }
            else if(cmpExp.op == CompareExpr.LT){
            	cmpExp.op = CompareExpr.GT;
            }
        }
    }

    private void printCmpExpr2(List<ColumnSpec> specList, Table LTable, Table RTable, List<Tuple> tupleList) {
        boolean printed = false;
        int attrList_index, i;
        int tuple_count = 0;
        if(tupleList==null){
        	Static.out.print("empty");
        	return;
        }
        for (tuple_count = 0; tuple_count < tupleList.size(); tuple_count += 2) {
            Tuple tuple = tupleList.get(tuple_count);
            Tuple tuple1 = tupleList.get(tuple_count + 1);
            for (ColumnSpec col : specList) {
                int count_space = 0;
                if (col.tableName.equals(LTable.tableName.toLowerCase())) {
                    attrList_index = LTable.columnDefList.indexOf(LTable.columnDefMap.get(col.columnName));
                    if (tuple.getAttr(attrList_index) instanceof IntAttr) {
                        for (i = 0; i < count_space; i++)
                            Static.out.print("       ");
                        Static.out.print(((IntAttr) tuple.getAttr(attrList_index)).val + "     ");
                    } else {
                        for (i = 0; i < count_space; i++)
                            Static.out.print("       ");
                        Static.out.print(((VarcharAttr) tuple.getAttr(attrList_index)).val + "     ");
                    }
                    printed = true;
                } else if (col.tableName.equals(RTable.tableName.toLowerCase())) {
                    attrList_index = RTable.columnDefList.indexOf(RTable.columnDefMap.get(col.columnName));
                    if (tuple1.getAttr(attrList_index) instanceof IntAttr) {
                        for (i = 0; i < count_space; i++)
                            Static.out.print("       ");
                        Static.out.print(((IntAttr) tuple1.getAttr(attrList_index)).val + "     ");
                    } else {
                        for (i = 0; i < count_space; i++)
                            Static.out.print("       ");
                        Static.out.print(((VarcharAttr) tuple1.getAttr(attrList_index)).val + "     ");
                    }
                    printed = true;
                }
                count_space++;
            }
            Static.out.println();
        }
        if (printed) {
            Static.out.println();
            printed = false;
        }
    }

    private void printSpecList(List<ColumnSpec> specList) {
        boolean first = false;
        for (ColumnSpec col : specList) {
            Static.out.print(col.tableName + "." + col.columnName + "     ");
            first = true;
        }
        if (first)
            Static.out.println();
    }

    /* check what is needed in selectList */
    private List<ColumnSpec> FindSpecList(Map<String, Table> tableList) {
        List<ColumnSpec> specList = new ArrayList<ColumnSpec>();
        for (ColumnSpec col : selectList) {
            if (col.tableName != null && col.columnName != null) {
                specList.add(new ColumnSpec(col.tableName, col.columnName));
            } else if (col.tableName != null && col.columnName == null) {
                Table tem = tableList.get(col.tableName);
                for (ColumnDef cd : tem.columnDefList) {
                    specList.add(new ColumnSpec(col.tableName, cd.columnName));
                }
            } else if (col.tableName == null && col.columnName == null) {
                for (TableSpec ts : fromList) {
                    Table tem = tableList.get(ts.tableName);
                    for (ColumnDef cd : tem.columnDefList) {
                        specList.add(new ColumnSpec(tem.tableName, cd.columnName));
                    }
                }
            }
        }
        return specList;
    }

    private void checkWhereExpr(Expr expr, Map<String, String> aliasTable, Map<String, Table> tableList) throws Exception {
        if (expr instanceof OrExpr) {
            op_type = or;
            leftComExp = checkComExpr(((OrExpr) expr).subExpr.get(0), aliasTable, tableList);
            rightComExp = checkComExpr(((OrExpr) expr).subExpr.get(1), aliasTable, tableList);
        } else if (expr instanceof AndExpr) {
            op_type = and;
            leftComExp = checkComExpr(((AndExpr) expr).subExpr.get(0), aliasTable, tableList);
            rightComExp = checkComExpr(((AndExpr) expr).subExpr.get(1), aliasTable, tableList);
        } else if (expr instanceof CompareExpr) {
            leftComExp = checkComExpr(expr, aliasTable, tableList);
        }

    }

    private CompareExpression checkComExpr(Expr expr, Map<String, String> aliasTable, Map<String, Table> tableList) throws Exception {
        CompareExpr expr1 = (CompareExpr) expr;
        int type = expr1.getType();
        boolean LIsString = false;
        Expr leftExp = expr1.getLeft();
        Expr rightExp = expr1.getRight();
        CompareLiteral LLit = null;
        CompareLiteral RLit = null;
        if (leftExp instanceof ColumnSpec) {
            ColumnSpec LExp = (ColumnSpec) leftExp;
            if (LExp.columnName != null) {
                checkColSpec(LExp, aliasTable, tableList);
                LIsString = checkAttrTypeIsString(LExp.tableName, LExp.columnName, tableList);
                LLit = new CompareLiteral(LExp.tableName, LExp.columnName);
            }
        } else if (leftExp instanceof LiteralExpr) {
            LiteralExpr LExp = (LiteralExpr) leftExp;
            switch (LExp.getType()) {
            case LiteralExpr.INTEGER:
                LLit = new CompareLiteral((int) LExp.value);
                break;
            case LiteralExpr.STRING:
                LIsString = true;
                LLit = new CompareLiteral((String) LExp.value);
                break;
            case LiteralExpr.NULL:
                Static.out.println("NULL");
                break;
            }
        }
        if (rightExp instanceof ColumnSpec) {
            ColumnSpec RExp = (ColumnSpec) rightExp;
            if (RExp.columnName != null) {
                checkColSpec(RExp, aliasTable, tableList);
                if (LIsString != checkAttrTypeIsString(RExp.tableName, RExp.columnName, tableList)) {
                    throw new Exception("Error: the attribute " + RExp.tableName + " " + RExp.columnName + " is compared with different type of value");
                }
                RLit = new CompareLiteral(RExp.tableName, RExp.columnName);
            }
        } else if (rightExp instanceof LiteralExpr) {
            LiteralExpr RExp = (LiteralExpr) rightExp;
            switch (RExp.getType()) {
            case LiteralExpr.INTEGER:
                RLit = new CompareLiteral((int) RExp.value);
                if (LIsString) {
                    throw new Exception("Error: " + (int) RExp.value + " can't be compared with String");
                }
                break;
            case LiteralExpr.STRING:
                if (!LIsString)
                    throw new Exception("Error: String can't be compared with Int");
                else {
                    RLit = new CompareLiteral((String) RExp.value);
                    break;
                }
            case LiteralExpr.NULL:
                Static.out.println("NULL");
                break;
            }
        }
        if (LIsString) {
            if (type != CompareExpr.EQ && type != CompareExpr.NEQ)
                throw new Exception("Error: String can only be compare with equal and unequal");
        }
        return new CompareExpression(LLit, RLit, type);
    }

    private boolean checkAttrTypeIsString(String tableName, String attrName, Map<String, Table> tableList) {
        Table t = tableList.get(tableName);
        ColumnDef d = t.columnDefMap.get(attrName);
        // TODO check VarcharDatatype
        if (d.datatype instanceof Datatype.VarcharDatatype)
            return true;
        return false;
    }

    private void checkAggrList(Map<String, String> aliasTable, Map<String, Table> tableList) throws Exception {
        if (aggregate.column.columnName != null) {
            checkColSpec(aggregate.column, aliasTable, tableList);
        } else {/* star */
            if (aggregate.column.tableName != null)
                aggregate.column.tableName = searchAlias(aggregate.column.tableName, aliasTable);
            aggrIsStar = true;
        }

    }

    private void checkSelectList(Map<String, String> aliasTable, Map<String, Table> tableList) throws Exception {
        for (ColumnSpec col : selectList) {
            checkColSpec(col, aliasTable, tableList);
        }
    }

    private void checkColSpec(ColumnSpec col, Map<String, String> aliasTable, Map<String, Table> tableList) throws Exception {
        boolean attr_name_pass;
        if (col.tableName != null) { /* maybe alias or name of table */
            col.tableName = searchAlias(col.tableName, aliasTable);
            Table tableInFromList = tableList.get(col.tableName);
            if (tableInFromList == null)
                throw new Exception("Error: No such table \"" + col.tableName + "\"");
            if (tableInFromList.columnDefMap.get(col.columnName) != null) {
                col.tableName = tableInFromList.tableName.toLowerCase();
            } else {
                if (col.columnName == null) { /* column is "*" */
                    // Static.out.println(col.tableName + " : *");
                } else
                    throw new Exception("Error: No such attribute in the tables mentioned in FROM-clause \"" + col.columnName + "\"");
            }
        } else {
            /* only attribute including "*" */
            attr_name_pass = false;
            for (TableSpec table : fromList) {
                Table tableInFromList = tableList.get(table.tableName);
                // Static.out.println("tableInFromList " +
                // tableInFromList.tableName);
                if (tableInFromList == null)
                    throw new Exception("Error: No such table \"" + table.tableName + "\"");
                if (tableInFromList.columnDefMap.get(col.columnName) != null) {
                    if (attr_name_pass)
                        throw new Exception("Error: Ambiguous name \"" + col.columnName + "\"\n" + "The name occurs at least two table " + col.tableName + " and " + tableInFromList.tableName);
                    attr_name_pass = true;
                    col.tableName = tableInFromList.tableName.toLowerCase();
                }
            }
            if (attr_name_pass == false) {
                if (col.columnName == null) { /* column is "*" */
                    // Static.out.println(" only *");
                } else
                    throw new Exception("Error: No such attribute in the tables mentioned in FROM-clause \"" + col.columnName + "\"");
            }
        }
    }

    private String searchAlias(String alias, Map<String, String> aliasTable) throws Exception {
        String temp;
        temp = aliasTable.get(alias);
        if (temp == null)
            throw new Exception("Error: There is no alias or tableName of " + alias + " defined in the FROM-clause");
        return temp;
    }

    private void selectFrom(Map<String, Table> tableList) {
        if (fromList.size() == 1) {
            Table t0 = tableList.get(fromList.get(0).tableName);
            
            Collection<Tuple> tuples;
            if (t0.primaryKey.indexing == ColumnDef.HASH_IDX) {
                tuples = t0.primaryKey.asHTreeMap().values();
            } else {
                tuples = t0.primaryKey.asBTreeMap().values();
            }
            
            if (selectList.get(0).columnName == null) { // SELECT *
                for (Tuple t : tuples) {
                    Static.out.print(t.getAttr(0));
                    for (int i = 1; i < t0.columnDefList.size(); i++) {
                        Static.out.print("|" + t.getAttr(i));
                    }
                    Static.out.println("|");
                }
                
            } else { // SELECT selected
                
                List<ColumnDef> selectedColumn = new ArrayList<>();
                
                for (ColumnSpec selected : selectList) {
                    ColumnDef column = t0.columnDefMap.get(selected.columnName);
                    selectedColumn.add(column);
                }
                
                for (Tuple t : tuples) {
                    for (ColumnDef column : selectedColumn) {
                        Attr attr = t.getAttr(column.index);
                        Static.out.print("|" + attr);
                    }
                    Static.out.println("|");
                }
            }  
            
        } else if (fromList.size() == 2) {
            Table t0 = tableList.get(fromList.get(0).tableName);
            Table t1 = tableList.get(fromList.get(1).tableName);
            ColumnSpec s0 = selectList.get(0);
            List<ColumnDef> selectedColumns = new ArrayList<>();
            
            Collection<Tuple> tuples0, tuples1;
            if (t0.primaryKey.indexing == ColumnDef.HASH_IDX) {
                tuples0 = t0.primaryKey.asHTreeMap().values();
            } else {
                tuples0 = t0.primaryKey.asBTreeMap().values();
            }
            
            if (t1.primaryKey.indexing == ColumnDef.HASH_IDX) {
                tuples1 = t1.primaryKey.asHTreeMap().values();
            } else {
                tuples1 = t1.primaryKey.asBTreeMap().values();
            }
            
            if (s0.columnName == null) { // <<< All Columns >>> (case 1)SELECT * // (case 2)SELECT table0.*,...
                if (s0.tableName == null) { // (case 1) SELECT *
                    for (ColumnDef column : t0.columnDefList) {
                        selectedColumns.add(column);
                    }
                    for (ColumnDef column : t1.columnDefList) {
                        selectedColumns.add(column);
                    }
                } else { // (case 2)
                    for (ColumnSpec cSpec : selectList) {
                        if (cSpec.tableName.equals(t0.tableName)) {
                            for (ColumnDef column : t0.columnDefList) {
                                selectedColumns.add(column);
                            }
                        } else {
                            for (ColumnDef column : t1.columnDefList) {
                                selectedColumns.add(column);
                            }
                        }
                    }
                }
            } else { // SELECT col, ... // SELECT table0.col, ...
                for (ColumnSpec cSpec : selectList) {
                    if (cSpec.tableName == null) {
                        ColumnDef column = t0.columnDefMap.get(cSpec.columnName);
                        if (column == null) t1.columnDefMap.get(cSpec.columnName);
                        selectedColumns.add(column);
                    } else if (cSpec.tableName.equals(t0.tableName)) {
                        ColumnDef column = t0.columnDefMap.get(cSpec.columnName);
                        selectedColumns.add(column);
                    } else {
                        ColumnDef column = t1.columnDefMap.get(cSpec.columnName);
                        selectedColumns.add(column);
                    }
                }
            }
            
            // Print out!!!
            for (Tuple tuple0 : tuples0) {
                for (Tuple tuple1 : tuples1) {
                    for (ColumnDef column : selectedColumns) {
                        if (column.table == t0) {
                            Static.out.print("|" + tuple0.getAttr(column.index));
                        } else {
                            Static.out.print("|" + tuple1.getAttr(column.index));
                        }
                    }
                    Static.out.println("|");
                }
            }
        } else {
            throw new RuntimeException("from-clause is too big for this implementation");
        }
    }

    /**
     * Calculate aggregates when where-clause does not exist.
     * @param tableList
     */
    public void aggr(Map<String, Table> tableList) {
        Table t0 = tableList.get(fromList.get(0).tableName);
        long answer;
        
        if (aggregate.type == Aggregate.COUNT) {
            if (fromList.size() == 1) {
                if (aggregate.column.tableName == null) {
                    answer = t0.primaryKey.getCountNotNull();
                } else {
                    ColumnDef column = t0.columnDefMap.get(aggregate.column.columnName);
                    answer = column.getCountNotNull();
                }
                
            } else if (fromList.size() == 2) {
                Table t1 = tableList.get(fromList.get(1).tableName);
                
                if (aggregate.column.tableName == null) {
                    answer = t0.primaryKey.getCountNotNull() * t1.primaryKey.getCountNotNull();
                } else if (aggregate.column.tableName.equals(t0)) {
                    ColumnDef column = t0.columnDefMap.get(aggregate.column.columnName);
                    answer = column.getCountNotNull() * t1.primaryKey.getCountNotNull();
                } else {
                    ColumnDef column = t1.columnDefMap.get(aggregate.column.columnName);
                    answer = column.getCountNotNull() * t0.primaryKey.getCountNotNull();
                }
            } else {
                throw new RuntimeException("from-clause is too big for this implementation");
            }
        } else {
            if (aggregate.column.columnName == null) { // SUM(*)
                throw new RuntimeException("ERROR: Illegal wildcard in aggregation SUM.");
            }
            
            ColumnDef column = t0.columnDefMap.get(aggregate.column.columnName);
            if (column.datatype instanceof Datatype.VarcharDatatype) { // SUM( varchar )
                throw new RuntimeException("ERROR: Illegal aggregation function SUM on varchar.");
            } else {
                answer = column.getSum();
            }
        }
        
        Static.out.println(answer);
        
    }
}