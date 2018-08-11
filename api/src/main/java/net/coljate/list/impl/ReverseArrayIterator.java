package net.coljate.list.impl;

import net.coljate.list.Array;
import net.coljate.list.ListIterator;

/**
 *
 * @author Ollie
 */
public class ReverseArrayIterator<T> implements ListIterator<T> {

    private final Array<? extends T> array;
    private int index;

    public ReverseArrayIterator(final Array<? extends T> array) {
        this.array = array;
        this.index = array.length();
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public T previous() {
        return array.get(--index);
    }

    @Override
    public boolean hasNext() {
        return index < array.length();
    }

    @Override
    public T next() {
        return array.get(index++);
    }

}
