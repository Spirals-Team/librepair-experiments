package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
public class Array<T> implements SimpleContainer<T> {
    private static final int DEFAULT_CAPACITY = 10;
    @GuardedBy("this")
    private Object[] container;
    private int modCount = 0;
    private int size;
    private int position = 0;

    public Array() {
        this.container = new Object[DEFAULT_CAPACITY];
    }

    public synchronized boolean add(T element) {
        if (size == container.length)
            container = Arrays.copyOf(container, size * 2);
        container[size++] = element;
        modCount++;
        return true;
    }

    public synchronized T get(int index) {
        return (T) container[index];
    }

    public synchronized Object[] toArray() {
        return Arrays.copyOf(container, size);
    }

    public synchronized int length() {
        return container.length;
    }

    private synchronized int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (container[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(container[i]))
                    return i;
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public synchronized Iterator<T> iterator() throws ConcurrentModificationException {
        int expectedModCount = modCount;

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public T next() throws NoSuchElementException {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException("ConcurrentModification!");
                }
                if (!hasNext()) {
                    throw new NoSuchElementException("No such element!");
                }

                return (T) container[position++];
            }
        };
    }
}
