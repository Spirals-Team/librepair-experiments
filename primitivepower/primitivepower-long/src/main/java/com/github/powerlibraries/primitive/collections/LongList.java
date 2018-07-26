package com.github.powerlibraries.primitive.collections;

import java.nio.LongBuffer;
import java.util.List;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongUnaryOperator;

import com.github.powerlibraries.primitive.common.LongPointer;

public interface LongList extends List<Long>, LongCollection {

	long getLong(int index);

	long setLong(int index, long element);

	void addLong(int index, long element);

	int indexOfLong(long o);

	int lastIndexOfLong(long o);

	@Override
	LongList subList(int fromIndex, int toIndex);
	
	@Override
	LongListIterator listIterator();
	
	@Override
	LongListIterator listIterator(int index);
	
	void sort();
	
	void parallelSort();
	
	@Override
	default Iterable<LongPointer> primitiveIterable() {
		return primitiveIterable(0);
	}
	
	Iterable<LongPointer> primitiveIterable(int index);
	
	default void replaceAll(LongUnaryOperator operator) {
		Objects.requireNonNull(operator);
		final LongListIterator li = this.listIterator();
		while (li.hasNext()) {
			li.setLong(operator.applyAsLong(li.nextLong()));
		}
	}
	
	@Override
	default Spliterator.OfLong spliterator() {
		return Spliterators.spliterator(iterator(), size(), Spliterator.ORDERED);
	}
	
	void reverse();
	
	@Override @Deprecated
	Long get(int index);

	@Override @Deprecated
	Long set(int index, Long element);

	@Override @Deprecated
	void add(int index, Long element);

	@Override @Deprecated
	int indexOf(Object o);

	@Override @Deprecated
	int lastIndexOf(Object o);
	
	long removeAt(int index);
	
	@Override @Deprecated
	Long remove(int index);
	
	LongBuffer asBuffer();
	
}