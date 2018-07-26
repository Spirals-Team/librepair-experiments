package com.github.powerlibraries.primitive.collections;

import java.util.Collection;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntPredicate;
import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public interface IntCollection extends Collection<Integer>, IntIterable {

	boolean containsInt(int o);
	
	Integer[] toArray(IntFunction<Integer[]> p);

	int[] toIntArray();

	boolean addInt(int e);

	boolean removeInt(int o);

	boolean containsAllInts(IntCollection c);

	boolean addAllInts(IntCollection c);

	boolean removeAllInts(IntCollection c);

	boolean retainAllInts(IntCollection c);
	
	default IntStream streamInts() {
		return StreamSupport.intStream(spliterator(), false);
	}

	default IntStream parallelStreamInts() {
		return StreamSupport.intStream(spliterator(), true);
	}
	
	@Override
	default Spliterator.OfInt spliterator() {
		return Spliterators.spliterator(this.iterator(), this.size(), 0);
	}
	
	default boolean removeIf(IntPredicate filter) {
		Objects.requireNonNull(filter);
		boolean removed = false;
		final PrimitiveIterator.OfInt each = iterator();
		while (each.hasNext()) {
			if (filter.test(each.nextInt())) {
				each.remove();
				removed = true;
			}
		}
		return removed;
	}
	
	@Override @Deprecated
	boolean contains(Object o);
	
	@Override @Deprecated
	<T> T[] toArray(T[] p);
	
	@Override @Deprecated
	Object[] toArray();

	@Override @Deprecated
	boolean add(Integer e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Integer> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
	@Override @Deprecated
	Stream<Integer> stream();

	@Override @Deprecated
	Stream<Integer> parallelStream();
	
	@Override @Deprecated	
	boolean removeIf(Predicate<? super Integer> filter);
	
}