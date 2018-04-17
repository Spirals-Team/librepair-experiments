package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class DynamicArrayIterator<E> implements Iterator<E> {

    private DynamicArray array;
    private Object[] objects;
    private int expectedModCount;
    private int index = 0;

    public DynamicArrayIterator(DynamicArray array) {
        this.array = array;
        this.objects = array.getContainer();
        this.expectedModCount = array.getModCount();
    }

    @Override
    public boolean hasNext() {
        this.checkModCount();
        boolean result = true;
        if (expectedModCount != array.getModCount()) {
            throw new ConcurrentModificationException();
        }
        if (index == objects.length || objects[index] == null) {
            result = false;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
        this.checkModCount();
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return (E) objects[index++];
    }

    private void checkModCount() {
        if (expectedModCount != array.getModCount()) {
            throw new ConcurrentModificationException();
        }
    }
}
