package com.gmail.chh9513136.simpledb.core;

import java.io.Serializable;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.mapdb.BTreeMap;
import org.mapdb.Fun;
import org.mapdb.Fun.Tuple2;
import org.mapdb.HTreeMap;

import com.gmail.chh9513136.simpledb.core.Datatype.IntDatatype;

public final class ColumnDef implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -575577857468597155L;
    
    public final String columnName;
    public final Datatype datatype;
    public final boolean isPrimaryKey;
    public final int indexing;
    
    Table table;
    int index;
    
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int countNotNull = 0;
    private long sum = 0;
    
    private transient Map<Comparable, Tuple> map;
    private transient Set<Fun.Tuple2<Comparable, Tuple>> multiMap;
    
    public static final int HASH_IDX = 1;
    public static final int TREE_IDX = 2;
    
    public ColumnDef(String columnName, Datatype datatype, boolean isPrimaryKey, int indexing) {
        this.columnName = columnName;
        this.datatype = datatype;
        this.isPrimaryKey = isPrimaryKey;
        this.indexing = indexing;
    }
    
    public BTreeMap<Comparable, Tuple> asBTreeMap() {
        return (BTreeMap<Comparable, Tuple>) map;
    }
    
    public HTreeMap<Comparable, Tuple> asHTreeMap() {
        return (HTreeMap<Comparable, Tuple>) map;
    }
    
    public NavigableSet<Fun.Tuple2<Comparable, Tuple>> asNavigableSet() {
        return (NavigableSet<Tuple2<Comparable, Tuple>>) multiMap;
    }
    
    public Set<Tuple2<Comparable, Tuple>> asSet() {
        return multiMap;
    }
    
    public void setMap(Map<Comparable, Tuple> map) {
        this.map = map;
    }
    
    public void setMultiMap(Set<Tuple2<Comparable, Tuple>> multiMap) {
        this.multiMap = multiMap;
    }
    
    void updateStats(Comparable value) {
        
        if (value != null) {
            countNotNull++;
            
            if (value instanceof Integer) {
                int val = (Integer) value;
                if (min > val) min = val;
                if (max < val) max = val;
                sum += val;
            }
        }
    }
    
    int getMin() { return min; }
    int getMax() { return max; }
    long getSum() { return sum; }
    int getCountNotNull() { return countNotNull; }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
        result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
        result = prime * result + (isPrimaryKey ? 1231 : 1237);
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
        ColumnDef other = (ColumnDef) obj;
        if (columnName == null) {
            if (other.columnName != null) {
                return false;
            }
        } else if (!columnName.equalsIgnoreCase(other.columnName)) {
            return false;
        }
        if (datatype == null) {
            if (other.datatype != null) {
                return false;
            }
        } else if (!datatype.equals(other.datatype)) {
            return false;
        }
        if (isPrimaryKey != other.isPrimaryKey) {
            return false;
        }
        return true;
    }
}
