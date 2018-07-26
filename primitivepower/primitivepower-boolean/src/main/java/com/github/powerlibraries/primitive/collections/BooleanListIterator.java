package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;

public interface BooleanListIterator extends ListIterator<Boolean> {
	@Override @Deprecated
	default Boolean next() {
		return nextBoolean();
	}

	@Override @Deprecated
	default Boolean previous() {
		return previousBoolean();
	}

	@Override @Deprecated
	default void set(Boolean e) {
		setBoolean(e);
	}

	@Override @Deprecated
	default void add(Boolean e) {
		addBoolean(e);
	}
	
	boolean nextBoolean();

	boolean previousBoolean();

	void setBoolean(boolean e);

	void addBoolean(boolean e);
}