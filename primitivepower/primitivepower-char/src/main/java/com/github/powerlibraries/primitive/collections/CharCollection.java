package com.github.powerlibraries.primitive.collections;

import java.util.Collection;

import java.util.function.IntFunction;

public interface CharCollection extends Collection<Character>, CharIterable {

	boolean containsChar(char o);
	
	Character[] toArray(IntFunction<Character[]> p);

	char[] toCharArray();

	boolean addChar(char e);

	boolean removeChar(char o);

	boolean containsAllChars(CharCollection c);

	boolean addAllChars(CharCollection c);

	boolean removeAllChars(CharCollection c);

	boolean retainAllChars(CharCollection c);
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override @Deprecated
	<T> T[] toArray(T[] p);
	
	@Override @Deprecated
	Object[] toArray();

	@Override @Deprecated
	boolean add(Character e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Character> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
}