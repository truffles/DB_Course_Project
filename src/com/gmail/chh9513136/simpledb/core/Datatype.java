package com.gmail.chh9513136.simpledb.core;

import java.io.Serializable;

public abstract class Datatype implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3165342717947226771L;

    public abstract Attr newAttr(Object value);
    public abstract String toString();
    
    public static final class IntDatatype extends Datatype {
        /**
         * 
         */
        private static final long serialVersionUID = -52736523697975652L;
        public static final IntDatatype INSTANCE = new IntDatatype();
        private IntDatatype() {}

        @Override
        public Attr newAttr(Object value) {
            if (value == NullDatatype.INSTANCE)
                return Attr.NullAttr.INSTANCE;
            if (value.getClass() == Integer.class)
                return new IntAttr((int) value);
            return null; // Rejected
        }

        @Override
        public String toString() {
            return "INT";
        }
    }
    
    public static final class VarcharDatatype extends Datatype {
        /**
         * 
         */
        private static final long serialVersionUID = -8357024416597866886L;
        public final int length;
        private static final VarcharDatatype[] pool = new VarcharDatatype[51];
        
        private VarcharDatatype(int length) {
            this.length = length;
        }
        
        public static VarcharDatatype newVarchar(int length) {
            if (pool[length] == null)
                return pool[length] = new VarcharDatatype(length);
            else
                return pool[length];
        }

        @Override
        public Attr newAttr(Object value) {
            if (value == NullDatatype.INSTANCE)
                return Attr.NullAttr.INSTANCE;
            if (value.getClass() == String.class && ((String)value).length() <= length)
                return new VarcharAttr((String) value);
            return null; // Rejected
        }

        @Override
        public String toString() {
            return "VARCHAR(" + length + ")";
        }
    }
    
    public static final class NullDatatype extends Datatype implements Comparable {
        /**
         * 
         */
        private static final long serialVersionUID = -6739989668376974701L;
        public static final Comparable INSTANCE = new NullDatatype();
        
        private NullDatatype() {}

        @Override
        public Attr newAttr(Object value) {
            return Attr.NullAttr.INSTANCE;
        }

        @Override
        public String toString() {
            return "NULL";
        }

        @Override
        public int compareTo(Object o) {
            if (o == INSTANCE)
                return 0;
            return -1;
        }
        
    }
}
