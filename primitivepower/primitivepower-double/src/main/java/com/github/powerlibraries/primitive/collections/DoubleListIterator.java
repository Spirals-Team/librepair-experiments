package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;
import java.util.PrimitiveIterator;
public interface DoubleListIterator extends ListIterator<Double>, PrimitiveIterator.OfDouble {
	@Override @Deprecated
	default Double next() {
		return nextDouble();
	}

	@Override @Deprecated
	default Double previous() {
		return previousDouble();
	}

	@Override @Deprecated
	default void set(Double e) {
		setDouble(e);
	}

	@Override @Deprecated
	default void add(Double e) {
		addDouble(e);
	}
	
	double nextDouble();

	double previousDouble();

	void setDouble(double e);

	void addDouble(double e);
}