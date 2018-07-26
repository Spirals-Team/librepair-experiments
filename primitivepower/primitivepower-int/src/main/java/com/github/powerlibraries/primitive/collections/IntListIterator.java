package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;
import java.util.PrimitiveIterator;
public interface IntListIterator extends ListIterator<Integer>, PrimitiveIterator.OfInt {
	@Override @Deprecated
	default Integer next() {
		return nextInt();
	}

	@Override @Deprecated
	default Integer previous() {
		return previousInt();
	}

	@Override @Deprecated
	default void set(Integer e) {
		setInt(e);
	}

	@Override @Deprecated
	default void add(Integer e) {
		addInt(e);
	}
	
	int nextInt();

	int previousInt();

	void setInt(int e);

	void addInt(int e);
}