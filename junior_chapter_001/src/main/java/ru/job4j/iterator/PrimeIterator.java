package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimeIterator implements Iterator {
    private int[] values;
    int index = 0;

    public PrimeIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        for (int i = index; i < values.length; i++) {
            boolean x = true;
            if (values[i] > 1) {
                for (int j = 2; j < values[i]; j++) {
                    if (values[i] % j == 0) {
                        x = false;
                        break;
                    }
                }
            }
            if (values[i] != 1 && x) {
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
