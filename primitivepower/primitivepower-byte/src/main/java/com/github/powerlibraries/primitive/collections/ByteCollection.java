package com.github.powerlibraries.primitive.collections;

import java.util.Collection;

import java.util.function.IntFunction;

public interface ByteCollection extends Collection<Byte>, ByteIterable {

	boolean containsByte(byte o);
	
	Byte[] toArray(IntFunction<Byte[]> p);

	byte[] toByteArray();

	boolean addByte(byte e);

	boolean removeByte(byte o);

	boolean containsAllBytes(ByteCollection c);

	boolean addAllBytes(ByteCollection c);

	boolean removeAllBytes(ByteCollection c);

	boolean retainAllBytes(ByteCollection c);
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override @Deprecated
	<T> T[] toArray(T[] p);
	
	@Override @Deprecated
	Object[] toArray();

	@Override @Deprecated
	boolean add(Byte e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Byte> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
}