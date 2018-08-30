package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable dynamic collection with fail-fast behavior.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class DynamicList<E> implements Iterable<E> {

    private Object[] container;
    private int modCount = 0;
    private int idx = 0;
    private int position = 0;

    public DynamicList() {
        this.container = new Object[1];
    }

    public DynamicList(int size) {
        this.container = new Object[size];
    }

    public DynamicList(E... args) {
        this.container = new Object[args.length];
        for (int i = 0; i < container.length; i++) {
            this.container[i] = args[i];
            idx++;
        }
    }

    public void add(E value) {
        if (idx == container.length) {
            Object[] newContainer = new Object[idx * 2];
            System.arraycopy(container, 0, newContainer, 0, container.length);
            container = newContainer;
        }
        modCount++;
        container[idx++] = value;
    }

    public E get(int index) {
        return (E) container[index];
    }

    public int length() {
        return container.length;
    }

    @Override
    public Iterator<E> iterator() {
        int expectedModCount = modCount;
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                checkContainerModification();
                return position < idx && container[position] != null;
            }

            @Override
            public E next() {
                checkContainerModification();
                if (hasNext()) {
                    Object item = container[position++];
                    return (E) item;
                }
                throw new NoSuchElementException();
            }

            private void checkContainerModification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
