package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;

public interface ObjectListIterator<E> extends ListIterator<E> {
	@Override @Deprecated
	default E next() {
		return nextObject();
	}

	@Override @Deprecated
	default E previous() {
		return previousObject();
	}

	@Override @Deprecated
	default void set(E e) {
		setObject(e);
	}

	@Override @Deprecated
	default void add(E e) {
		addObject(e);
	}
	
	E nextObject();

	E previousObject();

	void setObject(E e);

	void addObject(E e);
}