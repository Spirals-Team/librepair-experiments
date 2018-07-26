package com.github.powerlibraries.primitive.collections;

import java.util.Iterator;

import com.github.powerlibraries.primitive.common.BytePointer;

public interface ByteIterable extends Iterable<Byte> {
	
	@Override
	Iterator<Byte> iterator();
	
	Iterable<BytePointer> primitiveIterable();
	
}
