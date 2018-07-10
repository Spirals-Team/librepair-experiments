package ru.job4j.set;

import ru.job4j.list.LinkedList;

import java.util.Arrays;
import java.util.Iterator;

/**
 * HashSet implements backed by a hash table.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 * @see LinkedList
 */
public class HashSet<E> {

    private static final int DEFAULT_SIZE = 2;

    private Object[] container;
    private Object[] prevContainer;

    public HashSet() {
        this.container = new Object[DEFAULT_SIZE];
    }

    public boolean add(E value) {
        int idx = getIdx(value);
        if (contains(value)) {
            return false;
        }

        if (container[idx] == null) {
            LinkedList<E> list = new LinkedList<>(value);
            container[idx] = list;
        } else {
            LinkedList<E> list = (LinkedList<E>) container[idx];
            list.add(value);
        }
        return true;
    }

    public boolean contains(E value) {
        int idx = getIdx(value);
        if (container[idx] == null) {
            return false;
        }

        LinkedList<E> list = (LinkedList<E>) container[idx];
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            if (it.next() == value) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(E value) {
        int idx = getIdx(value);
        if (!contains(value)) {
            return false;
        }

        LinkedList<E> list = (LinkedList<E>) container[idx];
        return list.remove(value);
    }

    public void resize() {
        Object[] temp = container;
        container = new Object[container.length * DEFAULT_SIZE];
        prevContainer = temp;
        transfer();
    }

    private void transfer() {
        for (int idx = 0; idx < prevContainer.length; idx++) {
            if (prevContainer[idx] == null) {
                continue;
            }
            LinkedList<E> list = (LinkedList<E>) prevContainer[idx];
            Iterator<E> it = list.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
        }
        prevContainer = null;
    }

    @Override
    public String toString() {
        return Arrays.toString(container);
    }

    private int getIdx(E value) {
        int hash = getHash(value);
        return hash & (container.length - 1);
    }

    private int getHash(E value) {
        return value.hashCode();
    }
}
