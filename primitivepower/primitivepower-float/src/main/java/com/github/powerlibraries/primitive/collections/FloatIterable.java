package com.github.powerlibraries.primitive.collections;

import java.util.Iterator;

import com.github.powerlibraries.primitive.common.FloatPointer;

public interface FloatIterable extends Iterable<Float> {
	
	@Override
	Iterator<Float> iterator();
	
	Iterable<FloatPointer> primitiveIterable();
	
}
