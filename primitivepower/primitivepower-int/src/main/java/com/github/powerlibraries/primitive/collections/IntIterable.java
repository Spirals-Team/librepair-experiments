package com.github.powerlibraries.primitive.collections;

import java.util.PrimitiveIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

import com.github.powerlibraries.primitive.common.IntPointer;

public interface IntIterable extends Iterable<Integer> {
	
	@Override
	PrimitiveIterator.OfInt iterator();
	
	Iterable<IntPointer> primitiveIterable();
	
	default void forEach(IntConsumer action) {
		Objects.requireNonNull(action);
		for (IntPointer t : this.primitiveIterable()) {
			action.accept(t.get());
		}
	}

	default Spliterator.OfInt spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), 0);
	}
	
}
