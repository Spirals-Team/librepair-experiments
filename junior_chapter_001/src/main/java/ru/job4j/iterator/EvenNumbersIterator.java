package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator {
    private int[] values;
    int index = 0;

    public EvenNumbersIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        for (int i = index; i < values.length; i++) {
            if (values[i] % 2 == 0) {
                index = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public Object next() {
        if (hasNext()) {
            return values[index++];
        } else {
            throw new NoSuchElementException("NoSuchElementException!");
        }
    }
}
