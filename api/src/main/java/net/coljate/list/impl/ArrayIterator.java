package net.coljate.list.impl;

import net.coljate.list.Array;
import net.coljate.list.ListIterator;

/**
 *
 * @author Ollie
 */
public class ArrayIterator<T> implements ListIterator<T> {

    private final Array<? extends T> array;
    private int index;

    public ArrayIterator(final Array<? extends T> array) {
        this(array, 0);
    }

    public ArrayIterator(final Array<? extends T> array, final int startIndex) {
        this.array = array;
        this.index = startIndex;
    }

    protected int index() {
        return index;
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
        return index < array.count();
    }

    @Override
    public T next() {
        return array.get(index++);
    }

}
