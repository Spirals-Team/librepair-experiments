package com.github.powerlibraries.primitive.collections;

import java.util.Iterator;

import com.github.powerlibraries.primitive.common.ShortPointer;

public interface ShortIterable extends Iterable<Short> {
	
	@Override
	Iterator<Short> iterator();
	
	Iterable<ShortPointer> primitiveIterable();
	
}
