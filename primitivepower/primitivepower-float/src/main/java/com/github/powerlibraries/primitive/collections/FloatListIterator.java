package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;

public interface FloatListIterator extends ListIterator<Float> {
	@Override @Deprecated
	default Float next() {
		return nextFloat();
	}

	@Override @Deprecated
	default Float previous() {
		return previousFloat();
	}

	@Override @Deprecated
	default void set(Float e) {
		setFloat(e);
	}

	@Override @Deprecated
	default void add(Float e) {
		addFloat(e);
	}
	
	float nextFloat();

	float previousFloat();

	void setFloat(float e);

	void addFloat(float e);
}