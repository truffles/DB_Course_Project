package com.gmail.chh9513136.simpledb.core;

import java.io.Serializable;

public abstract class Attr implements Serializable, Comparable<Attr>
{
	/**
     * 
     */
    private static final long serialVersionUID = -345396949429284452L;
    public abstract String toString( int len );
	public abstract String toString();
	public abstract Comparable getValue();
	
	public boolean equals( Object a )
	{
		return false;
	}
	public static class NullAttr extends Attr{
		/**
         * 
         */
        private static final long serialVersionUID = 7304093379684737895L;
        public static final NullAttr INSTANCE = new NullAttr();
		private NullAttr(){};
		@Override
		public String toString( int len )
		{
			String val = "";
			String format = "-%" + len + "s-|";
			return String.format(format, val);
		}
		@Override
		public String toString() {
			return "<-NULL->";
		}
        @Override
        public Comparable getValue() {
            return null;
        }
        @Override
        public int compareTo(Attr o) {
            if (o == INSTANCE)
                return 0;
            return -1;
        }
	}
}
