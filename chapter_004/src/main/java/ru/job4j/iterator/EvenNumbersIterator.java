package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator {

    private ArrayList<Integer> list = new ArrayList<>();
    private int index = 0;

    public EvenNumbersIterator(final int[] values) {

        for (int i = 0; i < values.length; i++) {
            if (values[i] % 2 == 0) {
               list.add(values[i]);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(index++);
    }
}
