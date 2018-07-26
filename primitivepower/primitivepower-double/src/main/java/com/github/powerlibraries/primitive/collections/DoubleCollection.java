package com.github.powerlibraries.primitive.collections;

import java.util.Collection;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public interface DoubleCollection extends Collection<Double>, DoubleIterable {

	boolean containsDouble(double o);
	
	Double[] toArray(IntFunction<Double[]> p);

	double[] toDoubleArray();

	boolean addDouble(double e);

	boolean removeDouble(double o);

	boolean containsAllDoubles(DoubleCollection c);

	boolean addAllDoubles(DoubleCollection c);

	boolean removeAllDoubles(DoubleCollection c);

	boolean retainAllDoubles(DoubleCollection c);
	
	default DoubleStream streamDoubles() {
		return StreamSupport.doubleStream(spliterator(), false);
	}

	default DoubleStream parallelStreamDoubles() {
		return StreamSupport.doubleStream(spliterator(), true);
	}
	
	@Override
	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliterator(this.iterator(), this.size(), 0);
	}
	
	default boolean removeIf(DoublePredicate filter) {
		Objects.requireNonNull(filter);
		boolean removed = false;
		final PrimitiveIterator.OfDouble each = iterator();
		while (each.hasNext()) {
			if (filter.test(each.nextDouble())) {
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
	boolean add(Double e);

	@Override @Deprecated
	boolean remove(Object o);

	@Override @Deprecated
	boolean containsAll(Collection<?> c);

	@Override @Deprecated
	boolean addAll(Collection<? extends Double> c);

	@Override @Deprecated
	boolean removeAll(Collection<?> c);

	@Override @Deprecated
	boolean retainAll(Collection<?> c);
	
	@Override @Deprecated
	Stream<Double> stream();

	@Override @Deprecated
	Stream<Double> parallelStream();
	
	@Override @Deprecated	
	boolean removeIf(Predicate<? super Double> filter);
	
}