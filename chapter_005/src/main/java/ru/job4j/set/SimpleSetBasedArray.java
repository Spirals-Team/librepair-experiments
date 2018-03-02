package ru.job4j.set;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleSetBasedArray<E> implements Iterable<E> {

    private Object[] set;
    private int size = 0;

    public SimpleSetBasedArray() {
        this.set = new Object[10];
    }

    //if duplicate return true else false
    private boolean isDuplicate(E value) {

        for (int i = 0; i < size; i++) {
            if (set[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    //Add element if it is not duplicate.
    public boolean add(E value) {

        //Array expansion
        if (size >= set.length) {
            set = Arrays.copyOf(set, set.length + set.length);
        }

        //Checking duplicate
        if (isDuplicate(value)) {
            return false;
        }
        set[size++] = value;
        return true;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {

            int position = 0;

            @Override
            public boolean hasNext() {
                return size > position;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) set[position++];
            }
        };
    }
}
