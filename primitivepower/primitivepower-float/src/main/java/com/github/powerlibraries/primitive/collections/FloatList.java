package com.github.powerlibraries.primitive.collections;

import java.nio.FloatBuffer;
import java.util.List;

import com.github.powerlibraries.primitive.common.FloatPointer;

public interface FloatList extends List<Float>, FloatCollection {

	float getFloat(int index);

	float setFloat(int index, float element);

	void addFloat(int index, float element);

	int indexOfFloat(float o);

	int lastIndexOfFloat(float o);

	@Override
	FloatList subList(int fromIndex, int toIndex);
	
	@Override
	FloatListIterator listIterator();
	
	@Override
	FloatListIterator listIterator(int index);
	
	void sort();
	
	void parallelSort();
	
	@Override
	default Iterable<FloatPointer> primitiveIterable() {
		return primitiveIterable(0);
	}
	
	Iterable<FloatPointer> primitiveIterable(int index);
	
	void reverse();
	
	@Override @Deprecated
	Float get(int index);

	@Override @Deprecated
	Float set(int index, Float element);

	@Override @Deprecated
	void add(int index, Float element);

	@Override @Deprecated
	int indexOf(Object o);

	@Override @Deprecated
	int lastIndexOf(Object o);
	
	float removeAt(int index);
	
	@Override @Deprecated
	Float remove(int index);
	
	FloatBuffer asBuffer();
	
}