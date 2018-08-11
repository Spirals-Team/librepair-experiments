package net.coljate.counter.lazy;

import net.coljate.collection.Collection;
import net.coljate.collection.lazy.LazyCollection;
import net.coljate.counter.Counter;
import net.coljate.counter.ImmutableCounter;
import net.coljate.counter.MutableCounter;

/**
 *
 * @author Ollie
 */
public interface LazyCounter<T>
        extends LazyCollection<T>, Counter<T> {

    @Override
    default MutableCounter<T> mutableCopy() {
        return Counter.super.mutableCopy();
    }

    @Override
    default ImmutableCounter<T> immutableCopy() {
        return Counter.super.immutableCopy();
    }

    static <T> LazyCounter<T> count(final Collection<T> collection) {
        return new LazyElementCounter<>(collection);
    }

}
