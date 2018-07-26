package com.github.powerlibraries.primitive.common;

public class DefaultObjectPointer<E> implements ObjectPointer<E> {

	private E value;
	
	public void set(E value) {
		this.value = value;
	}

	@Override
	public E get() {
		return value;
	}
}
