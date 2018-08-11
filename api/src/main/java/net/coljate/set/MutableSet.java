package net.coljate.set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coljate.collection.MutableCollection;
import net.coljate.set.impl.MutableWrappedHashSet;
import net.coljate.set.impl.MutableWrappedSet;

/**
 *
 * @author Ollie
 */
public interface MutableSet<T> extends Set<T>, MutableCollection<T> {

    /**
     *
     * @param element
     * @return true if the element was added because it was not previously contained by this set, or false if it was.
     */
    @Override
    boolean add(T element);

    /**
     *
     * @param element
     * @return true if the given element was removed.
     */
    boolean remove(@Nullable Object element);

    @Deprecated
    default boolean removeFirst(@Nullable final Object element) {
        return this.remove(element);
    }

    @Override
    @Deprecated
    default int removeAll(@Nullable final Object element) {
        return this.remove(element) ? 1 : 0;
    }

    static <T> MutableSet<T> viewOf(@Nonnull final java.util.Set<T> set) {
        return new MutableWrappedSet<>(set);
    }

    static <T> MutableSet<T> createHashSet() {
        return MutableWrappedHashSet.create();
    }

    static <T> MutableSet<T> createHashSet(final int initialCapacity) {
        return MutableWrappedHashSet.create(initialCapacity);
    }

    @SafeVarargs
    static <T> MutableSet<T> copyIntoHashSet(final T... elements) {
        return MutableWrappedSet.copyIntoHashSet(elements);
    }

    static <T> MutableSet<T> copyIntoHashSet(final Iterable<? extends T> iterable) {
        return MutableWrappedSet.copyIntoHashSet(iterable);
    }

}
