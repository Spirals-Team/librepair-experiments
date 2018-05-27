package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ArrayContainer<T> implements SimpleContainer<T> {
    private Object[] container;
    private int modCount = 0;
    private int index = 0;
    private final int defaultSize = 10;

    public ArrayContainer() {
        this.container = new Object[defaultSize];
    }

    public ArrayContainer(int size) {
        if (size < 1) {
            size = defaultSize;
        }
        this.container = new Object[size];
    }

    @Override
    public void add(T o) {
        if (index == getSize()) {
            resize();
        }
        container[index++] = o;
        modCount++;
    }

    private void resize() {
        Object[] newArray = new Object[getSize() * 3 / 2 + 1];
        System.arraycopy(container, 0, newArray, 0, getSize());
        container = newArray;
    }

    @Override
    public T get(int index) {
        return (T) container[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int iterIndex = 0;
            private int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                return iterIndex < getSize();
            }

            @Override
            public T next() {
                if (iterIndex == getSize()) {
                    throw new NoSuchElementException("Not more elements.");
                } else if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("Iterator only for read.");
                }
                return (T) container[iterIndex++];
            }
        };
    }

    public int getSize() {
        return index;
    }

    public boolean contains(T t) {
        for (Object element: container) {
            if (element != null && element.equals(t)) {
                return true;
            }
        }
        return false;
    }
}
