package com.github.powerlibraries.primitive.collections;

import java.util.Collection;

import java.util.function.IntFunction;

public interface ObjectCollection<E> extends Collection<E>, ObjectIterable<E> {

	boolean containsObject(E o);
	
	E[] toArray(IntFunction<E[]> p);

	Object[] toObjectArray();

	boolean addObject(E e);

	boolean removeObject(E o);

	boolean containsAllObjects(ObjectCollection<? extends E> c);

	boolean addAllObjects(ObjectCollection<? extends E> c);

	boolean removeAllObjects(ObjectCollection<? extends E> c);

	boolean retainAllObjects(ObjectCollection<? extends E> c);
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override
	<T> T[] toArray(T[] p);
	
	@Override
	Object[] toArray();

	@Override @Deprecated
	boolean add(E e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends E> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
}