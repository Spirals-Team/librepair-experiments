package com.github.powerlibraries.primitive.collections;

import java.util.Collection;

import java.util.function.IntFunction;

public interface BooleanCollection extends Collection<Boolean>, BooleanIterable {

	boolean containsBoolean(boolean o);
	
	Boolean[] toArray(IntFunction<Boolean[]> p);

	boolean[] toBooleanArray();

	boolean addBoolean(boolean e);

	boolean removeBoolean(boolean o);

	boolean containsAllBooleans(BooleanCollection c);

	boolean addAllBooleans(BooleanCollection c);

	boolean removeAllBooleans(BooleanCollection c);

	boolean retainAllBooleans(BooleanCollection c);
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override @Deprecated
	<T> T[] toArray(T[] p);
	
	@Override @Deprecated
	Object[] toArray();

	@Override @Deprecated
	boolean add(Boolean e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Boolean> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
}