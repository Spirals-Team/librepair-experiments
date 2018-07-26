package com.github.powerlibraries.primitive.collections;

import java.util.PrimitiveIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

import com.github.powerlibraries.primitive.common.LongPointer;

public interface LongIterable extends Iterable<Long> {
	
	@Override
	PrimitiveIterator.OfLong iterator();
	
	Iterable<LongPointer> primitiveIterable();
	
	default void forEach(LongConsumer action) {
		Objects.requireNonNull(action);
		for (LongPointer t : this.primitiveIterable()) {
			action.accept(t.get());
		}
	}

	default Spliterator.OfLong spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), 0);
	}
	
}
