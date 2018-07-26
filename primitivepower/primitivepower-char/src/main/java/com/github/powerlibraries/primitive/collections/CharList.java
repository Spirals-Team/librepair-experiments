package com.github.powerlibraries.primitive.collections;

import java.nio.CharBuffer;
import java.util.List;

import com.github.powerlibraries.primitive.common.CharPointer;

public interface CharList extends List<Character>, CharCollection {

	char getChar(int index);

	char setChar(int index, char element);

	void addChar(int index, char element);

	int indexOfChar(char o);

	int lastIndexOfChar(char o);

	@Override
	CharList subList(int fromIndex, int toIndex);
	
	@Override
	CharListIterator listIterator();
	
	@Override
	CharListIterator listIterator(int index);
	
	void sort();
	
	void parallelSort();
	
	@Override
	default Iterable<CharPointer> primitiveIterable() {
		return primitiveIterable(0);
	}
	
	Iterable<CharPointer> primitiveIterable(int index);
	
	void reverse();
	
	@Override @Deprecated
	Character get(int index);

	@Override @Deprecated
	Character set(int index, Character element);

	@Override @Deprecated
	void add(int index, Character element);

	@Override @Deprecated
	int indexOf(Object o);

	@Override @Deprecated
	int lastIndexOf(Object o);
	
	char removeAt(int index);
	
	@Override @Deprecated
	Character remove(int index);
	
	CharBuffer asBuffer();
	
}