package ru.job4j.map;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HashMapIterator<V> implements Iterator<V> {
    private HashMap table;
    private Object[][] objects;
    private int expectedModCount;
    private int elem = 0;
    private int index = -1;

    public HashMapIterator(HashMap table) {
        this.table = table;
        objects = table.getHashArray();
        expectedModCount = table.getElems();
    }

    @Override
    public boolean hasNext() {
        checkModCount();
        return elem != expectedModCount;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        while (true) {
            if (objects[++index] != null) {
                elem++;
                return (V) objects[index][1];
            }
        }
    }

    private void checkModCount() {
        if (expectedModCount != table.getElems()) {
            throw new ConcurrentModificationException();
        }
    }
}
