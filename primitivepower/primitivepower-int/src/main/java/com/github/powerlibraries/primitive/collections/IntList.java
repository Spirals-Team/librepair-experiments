package com.github.powerlibraries.primitive.collections;

import java.nio.IntBuffer;
import java.util.List;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntUnaryOperator;

import com.github.powerlibraries.primitive.common.IntPointer;

public interface IntList extends List<Integer>, IntCollection {

	int getInt(int index);

	int setInt(int index, int element);

	void addInt(int index, int element);

	int indexOfInt(int o);

	int lastIndexOfInt(int o);

	@Override
	IntList subList(int fromIndex, int toIndex);
	
	@Override
	IntListIterator listIterator();
	
	@Override
	IntListIterator listIterator(int index);
	
	void sort();
	
	void parallelSort();
	
	@Override
	default Iterable<IntPointer> primitiveIterable() {
		return primitiveIterable(0);
	}
	
	Iterable<IntPointer> primitiveIterable(int index);
	
	default void replaceAll(IntUnaryOperator operator) {
		Objects.requireNonNull(operator);
		final IntListIterator li = this.listIterator();
		while (li.hasNext()) {
			li.setInt(operator.applyAsInt(li.nextInt()));
		}
	}
	
	@Override
	default Spliterator.OfInt spliterator() {
		return Spliterators.spliterator(iterator(), size(), Spliterator.ORDERED);
	}
	
	void reverse();
	
	@Override @Deprecated
	Integer get(int index);

	@Override @Deprecated
	Integer set(int index, Integer element);

	@Override @Deprecated
	void add(int index, Integer element);

	@Override @Deprecated
	int indexOf(Object o);

	@Override @Deprecated
	int lastIndexOf(Object o);
	
	int removeAt(int index);
	
	@Override @Deprecated
	Integer remove(int index);
	
	IntBuffer asBuffer();
	
}