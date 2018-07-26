package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;

public interface CharListIterator extends ListIterator<Character> {
	@Override @Deprecated
	default Character next() {
		return nextChar();
	}

	@Override @Deprecated
	default Character previous() {
		return previousChar();
	}

	@Override @Deprecated
	default void set(Character e) {
		setChar(e);
	}

	@Override @Deprecated
	default void add(Character e) {
		addChar(e);
	}
	
	char nextChar();

	char previousChar();

	void setChar(char e);

	void addChar(char e);
}