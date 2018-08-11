package net.coljate.list.impl;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.coljate.list.ListIterator;

/**
 *
 * @author Ollie
 */
public class WrappedListIterator<T> implements ListIterator<T> {

    private final java.util.ListIterator<? extends T> iterator;

    public WrappedListIterator(@Nonnull final java.util.ListIterator<? extends T> iterator) {
        this.iterator = Objects.requireNonNull(iterator);
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public T previous() {
        return iterator.previous();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

}
