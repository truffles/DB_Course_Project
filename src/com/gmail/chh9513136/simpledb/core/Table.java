package com.gmail.chh9513136.simpledb.core;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;

import org.mapdb.BTreeKeySerializer;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Fun;
import org.mapdb.Fun.Tuple2;
import org.mapdb.HTreeMap;

public class Table implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5790779843929586994L;
    public final String tableName;
    public final List<ColumnDef> columnDefList;
    public final ColumnDef primaryKey;
    public final Map<String, ColumnDef> columnDefMap = new HashMap<>();
    final boolean isAutoIncrement;
    private int nextAutoInc = 1;
    
    public transient DB db;
    

    public Table(String tableName, List<ColumnDef> columnDefList) throws Exception {
        ColumnDef pk = null;
        this.tableName = tableName;
        this.columnDefList = columnDefList;
        
        // Check duplicate attr name
        for (ColumnDef column : columnDefList) {
            if (columnDefMap.put(column.columnName.toLowerCase(), column) != null) {
                throw new Exception("Error: Duplicate attribute name \"" + column.columnName + "\"");
            }
            
            if (column.isPrimaryKey) {
                if (pk == null) {
                    pk = column;
                } else {
                    throw new Exception("Error: This DB do not support composite PK.");
                }
            }
        }
        
        isAutoIncrement = (pk == null);
        if (isAutoIncrement) {
            pk = new ColumnDef("autoIncrement", Datatype.IntDatatype.INSTANCE, true, ColumnDef.TREE_IDX);
        }
        primaryKey = pk;
        
        createOrReadBackDB();
    }
    
    public void createOrReadBackDB() {
        File f = new File("data/" + tableName + ".mydb");
        File p = f.getParentFile();
        if (p != null) {
            p.mkdirs();
        }
        
        db = DBMaker
                .newFileDB(f)
                .transactionDisable()
                //.concurrencyDisable()
                .mmapFileEnable()
                .asyncWriteEnable()
                .cacheSize(1048576)
                .commitFileSyncDisable()
                .make();
        
        for (int index = 0; index < columnDefList.size(); index++) {
            
            ColumnDef column = columnDefList.get(index);
            Map<Comparable, Tuple> map = null;
            Set<Fun.Tuple2<Comparable, Tuple>> multiMap = null;
            
            column.table = this;
            column.index = index;
            
            if (column.indexing == ColumnDef.HASH_IDX) {
                if (column.isPrimaryKey) {
                    map = db.createHashMap(column.columnName)
                            .counterEnable()
                            .makeOrGet();
                } else {
                    multiMap = db.createHashSet(column.columnName)
                            .counterEnable()
                            .makeOrGet();
                            
                }
            } else { // Tree
                if (column.isPrimaryKey) {
                    map = db.createTreeMap(column.columnName)
                            .counterEnable()
                            .nodeSize(32)
                            .makeOrGet();
                } else {
                    multiMap = db.createTreeSet(column.columnName)
                            .counterEnable()
                            .nodeSize(32)
                            .makeOrGet();
                }
            }
            
            column.setMap(map);
            column.setMultiMap(multiMap);
        }
        if (isAutoIncrement) {
            Map<Comparable, Tuple> map;
            map = db.createTreeMap("autoIncrement")
                    .counterEnable()
                    .nodeSize(32)
                    .makeOrGet();
            primaryKey.setMap(map);
            primaryKey.table = this;
            primaryKey.index = -1;
        }
    }

    public void addTuple(Tuple t) throws Exception {

        checkAttrSize(t);
        checkPKConstraint(t);

        if (primaryKey.indexing == ColumnDef.HASH_IDX) {
            /********** Insert into PK structure *********/
            HTreeMap<Comparable, Tuple> map = primaryKey.asHTreeMap();
            if (isAutoIncrement) {
                map.put(nextAutoInc++, t);
            } else {
                Comparable key = t.getPrimaryKey();
                Tuple oldValue = map.putIfAbsent(key, t);
                
                if (oldValue != null) {
                    throw new Exception("Error: Duplicated tuple already exists in Table \"" + this.tableName + "\" with values\n" + oldValue.toString());
                }
            }
            
        } else {
            /********** Insert into PK structure *********/
            BTreeMap<Comparable, Tuple> map = primaryKey.asBTreeMap();
            if (isAutoIncrement) {
                t.autoIncKey = nextAutoInc;
                map.put(nextAutoInc++, t);
            } else {
                Comparable key = t.getPrimaryKey();
                Tuple oldValue = map.putIfAbsent(key, t);
                
                if (oldValue != null) {
                    throw new Exception("Error: Duplicated tuple already exists in Table \"" + this.tableName + "\" with values\n" + oldValue.toString());
                }
            }
        }
        
        for (int i = 0; i < columnDefList.size(); i++) {
            ColumnDef column = columnDefList.get(i);
            Attr attr = t.getAttr(i);
            
            column.updateStats(attr.getValue());
            
            if (column.isPrimaryKey)
                continue;
            
            if (column.indexing == ColumnDef.HASH_IDX) {
                Set<Fun.Tuple2<Comparable, Tuple>> multiMap = column.asSet();
                multiMap.add(new Fun.Tuple2<Comparable, Tuple>(attr, t));
            } else {
                NavigableSet<Fun.Tuple2<Comparable, Tuple>> multiMap = column.asNavigableSet();
                multiMap.add(new Fun.Tuple2<Comparable, Tuple>(attr, t));
            }
        }
        
        
    }
    
    /**
     * This method is fxxking slow
     * @param column
     * @param attr
     * @return Set
     */
    Set<Tuple> getNotEquals(ColumnDef column, Attr attr) {
        if (column.isPrimaryKey) {
            if (column.indexing == ColumnDef.HASH_IDX) {
                Set<Tuple> set = new HashSet<>(column.asHTreeMap().values());
                set.remove(attr);
                return set;
            } else {
                Set<Tuple> set = new HashSet<>(column.asBTreeMap().values());
                set.remove(attr);
                return set;
            }
        } else {
            if (column.indexing == ColumnDef.HASH_IDX) {
                Set<Tuple> set = new HashSet<>();
                for (Fun.Tuple2<Comparable, Tuple> ent : column.asSet()) {
                    if (!ent.a.equals(attr)) {
                        set.add(ent.b);
                    }
                }
                return set;
            } else {
                Set<Tuple> set = new HashSet<>(internalPackAsSet(column.asNavigableSet()));
                for (Tuple t : Fun.filter(column.asNavigableSet(), attr)) {
                    set.remove(t);
                }
                return set;
            }
        }
    }
    
    /**
     * 
     * @param column
     * @param attr
     * @return Iterable or Collection
     */
    Iterable<Tuple> getEquals(ColumnDef column, Attr attr) {
        if (column.isPrimaryKey) {
            if (column.indexing == ColumnDef.HASH_IDX) {
            	Tuple t = column.asHTreeMap().get(attr);
                return (t == null)? Collections.EMPTY_SET : Collections.singleton(t);
            } else {
            	Tuple t = column.asBTreeMap().get(attr);
                return (t == null)? Collections.EMPTY_SET : Collections.singleton(t);
            }
        } else {
            if (column.indexing == ColumnDef.HASH_IDX) {
                Set<Tuple> set = new HashSet<>();
                for (Fun.Tuple2<Comparable, Tuple> ent : column.asSet()) {
                    if (ent.a.equals(attr)) {
                        set.add(ent.b);
                    }
                }
                return set;
            } else {
                return Fun.filter(column.asNavigableSet(), attr);
            }
        }
    }
    
    /**
     * Beware!!! May return null.
     * @param column
     * @param low
     * @param lowInclusive
     * @param hi
     * @param hiInclusive
     * @return null, Iterable or Collection
     */
    Iterable<Tuple> getRangeFast(ColumnDef column, IntAttr low, boolean lowInclusive, IntAttr hi, boolean hiInclusive) {
        if (column.isPrimaryKey) {
            if (column.indexing == ColumnDef.HASH_IDX) {
                if (column.getMax() < low.val || column.getMin() > hi.val) return Collections.emptySet();
                if (!lowInclusive && column.getMax() == low.val) return Collections.emptySet();
                if (!hiInclusive && column.getMin() == hi.val) return Collections.emptySet();
                
                if ((column.getMin() > low.val || lowInclusive && column.getMin() >= low.val)
                    && (column.getMax() < hi.val || hiInclusive && column.getMax() <= hi.val)) {
                        return column.asHTreeMap().values();
                }
                return null;
            } else {
                return column.asBTreeMap().subMap(low, lowInclusive, hi, hiInclusive).values();
            }
        } else {
            if (column.indexing == ColumnDef.HASH_IDX) {
                if (column.getMax() < low.val || column.getMin() > hi.val) return Collections.emptySet();
                if (!lowInclusive && column.getMax() == low.val) return Collections.emptySet();
                if (!hiInclusive && column.getMin() == hi.val) return Collections.emptySet();
                
                
                if ((column.getMin() > low.val || lowInclusive && column.getMin() >= low.val)
                    && (column.getMax() < hi.val || hiInclusive && column.getMax() <= hi.val)) {
                        return internalPackAsSet(column.asSet());
                }
                return null;
            } else {
                return Fun.filter(column.asNavigableSet(), low, lowInclusive, hi, hiInclusive);
            }
        }
    }
    
    /**
     * Beware!!! Use the fast one first.
     * @param column
     * @param low
     * @param lowInclusive
     * @param hi
     * @param hiInclusive
     * @return Collection
     */
    Collection<Tuple> getRangeSlow(ColumnDef column, IntAttr low, boolean lowInclusive, IntAttr hi, boolean hiInclusive) {
        Set<Tuple> set = new HashSet<>();
        if (column.isPrimaryKey) {
            for (Entry<Comparable, Tuple> ent : column.asHTreeMap().entrySet()) {
                Integer value = (Integer) ((Attr)ent.getKey()).getValue();
                if (value == null)
                    continue;
                if ((value > low.val || lowInclusive && value >= low.val)
                        && (value < hi.val || hiInclusive && value <= hi.val)) {
                    set.add(ent.getValue());
                }
            }
        } else {
            for (Fun.Tuple2<Comparable, Tuple> ent : column.asSet()) {
                Integer value = (Integer) ((Attr)ent.a).getValue();
                if (value == null)
                    continue;
                if ((value > low.val || lowInclusive && value >= low.val)
                        && (value < hi.val || hiInclusive && value <= hi.val)) {
                    set.add(ent.b);
                }
            }
        }
        return set;
    }
    
    static Set<Tuple> packAsSet(Iterable<Tuple> tuples) {
        Set<Tuple> set = new HashSet<>();
        for (Tuple entry : tuples) {
            set.add(entry);
        }
        return set;
    }
    
    private Set<Tuple> internalPackAsSet(Iterable<Fun.Tuple2<Comparable, Tuple>> collection) {
        Set<Tuple> set = new HashSet<>();
        for (Fun.Tuple2<Comparable, Tuple> entry : collection) {
            set.add(entry.b);
        }
        return set;
    }

    private void checkAttrSize(Tuple t) throws Exception {
        if (columnDefList.size() != t.getAttrSize()) {
            throw new Exception("Error: Attribute number mismatch: expected " + columnDefList.size() + ", got " + t.getAttrSize());
        }
    }

    private void checkPKConstraint(Tuple t) throws Exception {
        for (int i = 0; i < columnDefList.size(); i++) {
            ColumnDef column = columnDefList.get(i);
            Attr attr = t.getAttr(i);

            if (column.isPrimaryKey && attr == Attr.NullAttr.INSTANCE) {
                throw new Exception("Error: Primary key \"" + this.columnDefList.get(i).columnName + "\" cannot be null");
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tableName == null) ? 0 : tableName.toLowerCase().hashCode());
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
        Table other = (Table) obj;
        if (tableName == null) {
            if (other.tableName != null) {
                return false;
            }
        } else if (!tableName.equalsIgnoreCase(other.tableName)) {
            return false;
        }
        return true;
    }
    
}
