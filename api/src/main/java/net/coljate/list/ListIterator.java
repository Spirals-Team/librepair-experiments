package net.coljate.list;

import java.util.Iterator;

import net.coljate.list.impl.WrappedListIterator;

/**
 *
 * @author Ollie
 * @see java.util.ListIterator
 */
public interface ListIterator<T> extends Iterator<T> {

    boolean hasPrevious();

    T previous();

    static <T> ListIterator<T> wrap(final ListIterator<? extends T> iterator) {
        return (ListIterator<T>) iterator;
    }

    static <T> ListIterator<T> wrap(final java.util.ListIterator<? extends T> iterator) {
        return new WrappedListIterator<>(iterator);
    }

}
