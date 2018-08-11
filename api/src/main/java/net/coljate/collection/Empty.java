package net.coljate.collection;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;

import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public interface Empty<T> extends ImmutableCollection<T>, Externalizable {

    @Override
    @TimeComplexity(Complexity.CONSTANT)
    default boolean isEmpty() {
        return true;
    }

    @Override
    default boolean isAlwaysEmpty() {
        return true;
    }

    @Override
    @TimeComplexity(Complexity.CONSTANT)
    default boolean contains(final Object object) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    default <R> Empty<R> transform(final Function<? super T, ? extends R> transformation) {
        return (Empty<R>) this;
    }

    @Override
    default Empty<T> filter(final Predicate<? super T> predicate) {
        return this;
    }

    @Override
    default UnmodifiableIterator<T> iterator() {
        return UnmodifiableIterator.of();
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    default void writeExternal(final ObjectOutput out) throws IOException {
    }

    @Override
    default void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
    }

}
