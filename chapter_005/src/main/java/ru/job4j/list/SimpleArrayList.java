package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SimpleArrayList<E> implements Iterable<E> {
    @GuardedBy("this")
    private volatile Object[] container;
    @GuardedBy("this")
    private volatile int size = 0;
    @GuardedBy("this")
    private volatile int modCount = 0;

    public SimpleArrayList() {
        this.container = new Object[10];
    }

    public SimpleArrayList(int capacity) {
        if (capacity > 0) {
            this.container = new Object[capacity];
        }
    }
    // add element
    public synchronized void add(E value) {
        modCount++;
        if (size >= container.length) {
            container = Arrays.copyOf(container, container.length + container.length);
        }
        container[size++] = value;
    }
    // get element by index
    @SuppressWarnings("unchecked")
    public synchronized E get(int index) {
        return (E) container[index];
    }

    @Override
    public synchronized Iterator<E> iterator() {
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
                return (E) container[position++];
            }
        };
    }
}
