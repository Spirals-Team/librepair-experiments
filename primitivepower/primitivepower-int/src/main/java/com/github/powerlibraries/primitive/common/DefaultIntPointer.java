package com.github.powerlibraries.primitive.common;

public class DefaultIntPointer implements IntPointer {

	private int value;
	
	public void set(int value) {
		this.value = value;
	}

	@Override
	public int get() {
		return value;
	}
}
