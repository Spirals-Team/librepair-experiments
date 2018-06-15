package ru.job4j.container;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dynamic<E> implements Iterable<E> {
    private int index = 0;
    private int count = 0;
    protected transient int modCount = 0;
    private final static int INIT_SIZE = 10;
    private Object[] container = new Object[INIT_SIZE];

    public void add(E value) {
        if (index == container.length - 1) {
            reSize(container.length * 2);
        }
        this.container[index++] = value;
    }

    public E get(int position) {
        return (E) this.container[position];
    }

    public int size() {
        return index;
    }

    private void reSize(int newLength) {
        modCount++;
        Object[] newArray = new Object[newLength];
        System.arraycopy(container, 0, newArray, 0, index);
        container = newArray;
    }

    public boolean isEmpty() {
        return size() < 1;
    }

    int expectedModCount = modCount;


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return index > count;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("this collection has undergone a change");

                }
                if (!hasNext()) {
                    throw new NoSuchElementException("no such element");
                }
                return (E) container[count++];
            }
        };
    }
}
