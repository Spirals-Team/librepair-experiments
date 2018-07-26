package com.github.powerlibraries.primitive.collections;

import java.util.Collection;

import java.util.function.IntFunction;

public interface FloatCollection extends Collection<Float>, FloatIterable {

	boolean containsFloat(float o);
	
	Float[] toArray(IntFunction<Float[]> p);

	float[] toFloatArray();

	boolean addFloat(float e);

	boolean removeFloat(float o);

	boolean containsAllFloats(FloatCollection c);

	boolean addAllFloats(FloatCollection c);

	boolean removeAllFloats(FloatCollection c);

	boolean retainAllFloats(FloatCollection c);
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override @Deprecated
	<T> T[] toArray(T[] p);
	
	@Override @Deprecated
	Object[] toArray();

	@Override @Deprecated
	boolean add(Float e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Float> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
}