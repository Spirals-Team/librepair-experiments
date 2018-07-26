package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;

public interface ShortListIterator extends ListIterator<Short> {
	@Override @Deprecated
	default Short next() {
		return nextShort();
	}

	@Override @Deprecated
	default Short previous() {
		return previousShort();
	}

	@Override @Deprecated
	default void set(Short e) {
		setShort(e);
	}

	@Override @Deprecated
	default void add(Short e) {
		addShort(e);
	}
	
	short nextShort();

	short previousShort();

	void setShort(short e);

	void addShort(short e);
}