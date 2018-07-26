package com.github.powerlibraries.primitive.collections;

import java.util.Collection;

import java.util.function.IntFunction;

public interface ShortCollection extends Collection<Short>, ShortIterable {

	boolean containsShort(short o);
	
	Short[] toArray(IntFunction<Short[]> p);

	short[] toShortArray();

	boolean addShort(short e);

	boolean removeShort(short o);

	boolean containsAllShorts(ShortCollection c);

	boolean addAllShorts(ShortCollection c);

	boolean removeAllShorts(ShortCollection c);

	boolean retainAllShorts(ShortCollection c);
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override @Deprecated
	<T> T[] toArray(T[] p);
	
	@Override @Deprecated
	Object[] toArray();

	@Override @Deprecated
	boolean add(Short e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Short> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
}