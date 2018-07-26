package com.github.powerlibraries.primitive.collections;

import java.util.PrimitiveIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

import com.github.powerlibraries.primitive.common.DoublePointer;

public interface DoubleIterable extends Iterable<Double> {
	
	@Override
	PrimitiveIterator.OfDouble iterator();
	
	Iterable<DoublePointer> primitiveIterable();
	
	default void forEach(DoubleConsumer action) {
		Objects.requireNonNull(action);
		for (DoublePointer t : this.primitiveIterable()) {
			action.accept(t.get());
		}
	}

	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), 0);
	}
	
}
