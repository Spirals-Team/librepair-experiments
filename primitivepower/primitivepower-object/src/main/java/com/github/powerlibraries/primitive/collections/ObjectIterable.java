package com.github.powerlibraries.primitive.collections;

import java.util.Iterator;

import com.github.powerlibraries.primitive.common.ObjectPointer;

public interface ObjectIterable<E> extends Iterable<E> {
	
	@Override
	Iterator<E> iterator();
	
	Iterable<ObjectPointer<E>> primitiveIterable();
	
}
