package net.coljate.counter;

import net.coljate.collection.ImmutableCollection;
import net.coljate.counter.impl.MutableHashCounter;
import net.coljate.map.ImmutableMap;

/**
 *
 * @author Ollie
 */
public interface ImmutableCounter<T>
        extends Counter<T>, ImmutableCollection<T> {

    @Override
    ImmutableMap<T, Integer> countElements();

    @Override
    @Deprecated
    default ImmutableCounter<T> immutableCopy() {
        return this;
    }

    static <T> ImmutableCounter<T> copyOf(final Iterable<? extends T> iterable) {
        return MutableHashCounter.<T>copyOf(iterable).immutableCopy();
    }

}
