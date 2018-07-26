package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;
import java.util.PrimitiveIterator;
public interface LongListIterator extends ListIterator<Long>, PrimitiveIterator.OfLong {
	@Override @Deprecated
	default Long next() {
		return nextLong();
	}

	@Override @Deprecated
	default Long previous() {
		return previousLong();
	}

	@Override @Deprecated
	default void set(Long e) {
		setLong(e);
	}

	@Override @Deprecated
	default void add(Long e) {
		addLong(e);
	}
	
	long nextLong();

	long previousLong();

	void setLong(long e);

	void addLong(long e);
}