package com.gmail.chh9513136.simpledb.core;

public class CompareExpression {
	CompareLiteral left;
	CompareLiteral right;
	int op;
	public CompareExpression(CompareLiteral left, CompareLiteral right, int op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}
}
