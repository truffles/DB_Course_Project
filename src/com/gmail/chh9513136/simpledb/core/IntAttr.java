package com.gmail.chh9513136.simpledb.core;

public class IntAttr extends Attr
{
	int val;
	public IntAttr( int val )
	{
		this.val = val;
	}
	@Override
	public String toString( int len )
	{
		String format = " %" + len + "d |";
		return String.format(format, val);
	}
	@Override
	public String toString( )
	{
		return ( ( Integer )val ).toString(); 
	}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + val;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IntAttr other = (IntAttr) obj;
        if (val != other.val) {
            return false;
        }
        return true;
    }
    @Override
    public Comparable getValue() {
        return val;
    }
    
    public int getIntValue() {
        return val;
    }
    @Override
    public int compareTo(Attr o) {
        if (o == Attr.NullAttr.INSTANCE)
            return 1;
        int y = (Integer) o.getValue();
        return (val < y) ? -1 : ((val == y) ? 0 : 1);
    }

}
