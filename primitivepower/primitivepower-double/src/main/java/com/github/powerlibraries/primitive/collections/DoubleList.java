package com.github.powerlibraries.primitive.collections;

import java.nio.DoubleBuffer;
import java.util.List;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleUnaryOperator;

import com.github.powerlibraries.primitive.common.DoublePointer;

public interface DoubleList extends List<Double>, DoubleCollection {

	double getDouble(int index);

	double setDouble(int index, double element);

	void addDouble(int index, double element);

	int indexOfDouble(double o);

	int lastIndexOfDouble(double o);

	@Override
	DoubleList subList(int fromIndex, int toIndex);
	
	@Override
	DoubleListIterator listIterator();
	
	@Override
	DoubleListIterator listIterator(int index);
	
	void sort();
	
	void parallelSort();
	
	@Override
	default Iterable<DoublePointer> primitiveIterable() {
		return primitiveIterable(0);
	}
	
	Iterable<DoublePointer> primitiveIterable(int index);
	
	default void replaceAll(DoubleUnaryOperator operator) {
		Objects.requireNonNull(operator);
		final DoubleListIterator li = this.listIterator();
		while (li.hasNext()) {
			li.setDouble(operator.applyAsDouble(li.nextDouble()));
		}
	}
	
	@Override
	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliterator(iterator(), size(), Spliterator.ORDERED);
	}
	
	void reverse();
	
	@Override @Deprecated
	Double get(int index);

	@Override @Deprecated
	Double set(int index, Double element);

	@Override @Deprecated
	void add(int index, Double element);

	@Override @Deprecated
	int indexOf(Object o);

	@Override @Deprecated
	int lastIndexOf(Object o);
	
	double removeAt(int index);
	
	@Override @Deprecated
	Double remove(int index);
	
	DoubleBuffer asBuffer();
	
}