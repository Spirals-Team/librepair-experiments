package com.github.powerlibraries.primitive.common;

public class DefaultLongPointer implements LongPointer {

	private long value;
	
	public void set(long value) {
		this.value = value;
	}

	@Override
	public long get() {
		return value;
	}
}
