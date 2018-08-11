package net.coljate.collection;

import net.coljate.MutableIterableExtension;
import net.coljate.collection.impl.MutableWrappedCollection;
import net.coljate.list.MutableList;
import net.coljate.list.impl.MutableWrappedList;

/**
 *
 * @author Ollie
 */
public interface MutableCollection<T> extends Collection<T>, MutableIterableExtension<T> {

    /**
     * Add the given element to some location within this collection.
     *
     * @param element
     * @return true if the element was added, or false if it was not.
     */
    boolean add(T element);

    default boolean addAll(final Iterable<? extends T> iterable) {
        boolean addedAny = false;
        for (final T element : iterable) {
            addedAny |= this.add(element);
        }
        return addedAny;
    }

    /**
     * Bridge from a Java native collection. This method does not check at run-time if the collection is actually
     * mutable (for example it may be {@link java.util.Collections#unmodifiableCollection unmodifiable}).
     */
    static <T> MutableCollection<T> viewOf(final java.util.Collection<T> collection) {
        return MutableWrappedCollection.viewOf(collection);
    }

    @SafeVarargs
    static <T> MutableCollection<T> copyOf(final T... elements) {
        return MutableWrappedList.createArrayList(elements);
    }

    static <T> MutableCollection<T> copyOf(final Collection<? extends T> collection) {
        return MutableList.copyIntoArray(collection);
    }

}
