package net.coljate.set;

import java.util.Comparator;

import net.coljate.set.impl.MutableWrappedTreeSet;

/**
 *
 * @author Ollie
 */
public interface MutableSortedSet<T>
        extends SortedSet<T>, MutableSet<T> {

    @Override
    MutableSortedSet<T> mutableCopy();

    static <T> MutableSortedSet<T> createTreeSet(final Comparator<? super T> comparator) {
        return MutableWrappedTreeSet.create(comparator);
    }

}
