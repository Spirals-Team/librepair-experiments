package net.coljate.collection;

import net.coljate.collection.sorting.SortingAlgorithm;
import net.coljate.sequence.FiniteSequence;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Has a specific order, based on some {@link #comparator}.
 *
 * @author Ollie
 */
public interface SortedCollection<T> extends FiniteSequence<T> {

    /**
     * @return the comparator used to sort this collection. (Not the {@link SortingAlgorithm} algorithm!)
     */
    @Nonnull
    Comparator<? super T> comparator();

    @CheckForNull
    default T least() {
        return this.first();
    }

    /**
     * @return the greatest or last element in this collection, according to the sort.
     */
    @CheckForNull
    T greatest();

    @Override
    default T first() {
        final Iterator<T> iterator = this.iterator();
        return iterator.hasNext() ? null : iterator.next();
    }

}
