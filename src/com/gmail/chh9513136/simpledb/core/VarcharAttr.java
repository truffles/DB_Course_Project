package com.gmail.chh9513136.simpledb.core;

public class VarcharAttr extends Attr
{
	String val;
	public VarcharAttr( String val )
	{
		this.val = val;
	}
	@Override
	public String toString(int len)
	{
		String format = " %" + len + "s |";
		return String.format(format, val);
	}
	@Override
	public String toString( )
	{
		return val; 
	}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((val == null) ? 0 : val.hashCode());
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
        VarcharAttr other = (VarcharAttr) obj;
        if (val == null) {
            if (other.val != null) {
                return false;
            }
        } else if (!val.equals(other.val)) {
            return false;
        }
        return true;
    }
    @Override
    public Comparable getValue() {
        return val;
    }
    
    public String getStringValue() {
        return val;
    }
    @Override
    public int compareTo(Attr o) {
        if (o == Attr.NullAttr.INSTANCE) return 1;
        return val.compareTo((String) o.getValue());
    }

}