package com.github.powerlibraries.primitive.collections;

import java.util.Iterator;

import com.github.powerlibraries.primitive.common.BooleanPointer;

public interface BooleanIterable extends Iterable<Boolean> {
	
	@Override
	Iterator<Boolean> iterator();
	
	Iterable<BooleanPointer> primitiveIterable();
	
}
