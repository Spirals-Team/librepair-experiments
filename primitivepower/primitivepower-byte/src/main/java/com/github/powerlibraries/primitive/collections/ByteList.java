package com.github.powerlibraries.primitive.collections;

import java.nio.ByteBuffer;
import java.util.List;

import com.github.powerlibraries.primitive.common.BytePointer;

public interface ByteList extends List<Byte>, ByteCollection {

	byte getByte(int index);

	byte setByte(int index, byte element);

	void addByte(int index, byte element);

	int indexOfByte(byte o);

	int lastIndexOfByte(byte o);

	@Override
	ByteList subList(int fromIndex, int toIndex);
	
	@Override
	ByteListIterator listIterator();
	
	@Override
	ByteListIterator listIterator(int index);
	
	void sort();
	
	void parallelSort();
	
	@Override
	default Iterable<BytePointer> primitiveIterable() {
		return primitiveIterable(0);
	}
	
	Iterable<BytePointer> primitiveIterable(int index);
	
	void reverse();
	
	@Override @Deprecated
	Byte get(int index);

	@Override @Deprecated
	Byte set(int index, Byte element);

	@Override @Deprecated
	void add(int index, Byte element);

	@Override @Deprecated
	int indexOf(Object o);

	@Override @Deprecated
	int lastIndexOf(Object o);
	
	byte removeAt(int index);
	
	@Override @Deprecated
	Byte remove(int index);
	
	ByteBuffer asBuffer();
	
}