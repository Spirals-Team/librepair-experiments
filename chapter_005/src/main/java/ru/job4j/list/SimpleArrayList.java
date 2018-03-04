package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SimpleArrayList<E> implements Iterable<E> {
    private Object[] container;
    private int size = 0;
    private int modCount = 0;
    private final Object lock = new Object();

    public SimpleArrayList() {
        this.container = new Object[10];
    }

    public SimpleArrayList(int capacity) {
        if (capacity > 0) {
            this.container = new Object[capacity];
        }
    }
    // add element
    public void add(E value) {
        synchronized (lock) {
            modCount++;
            if (size >= container.length) {
                container = Arrays.copyOf(container, container.length + container.length);
            }
            container[size++] = value;
        }
    }
    // get element by index
    @SuppressWarnings("unchecked")
    public synchronized E get(int index) {
        synchronized (lock) {
            return (E) container[index];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int position = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                synchronized (lock) {
                    return (E) container[position++];
                }
            }
        };
    }
}
