package net.coljate.counter;

import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.counter.impl.MutableHashCounter;
import net.coljate.map.Map;
import net.coljate.map.lazy.LazyMap;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public interface Counter<T> extends Collection<T> {

    int count(Object element);

    @Override
    default boolean contains(final Object element) {
        return this.count(element) > 0;
    }

    @Nonnull
    Set<T> elements();

    @Nonnull
    default Map<T, Integer> countElements() {
        return LazyMap.transformValues(this.elements(), this::count);
    }
    
    @Override
    default MutableCounter<T> mutableCopy() {
        return MutableCounter.copyOf(this);
    }

    @Override
    default ImmutableCounter<T> immutableCopy() {
        return ImmutableCounter.copyOf(this);
    }

    static <T> MutableCounter<T> create() {
        return MutableHashCounter.create();
    }

}
