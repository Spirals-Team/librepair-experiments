package com.github.powerlibraries.primitive.common;

public class DefaultShortPointer implements ShortPointer {

	private short value;
	
	public void set(short value) {
		this.value = value;
	}

	@Override
	public short get() {
		return value;
	}
}
