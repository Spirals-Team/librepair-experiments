package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator {

    private final int[] values;
    private int index = 0;

    public EvenNumbersIterator(final int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return hasNextEvenIndex();
    }

    private int getNextEvenIndex(int currIndex) {

        int result = index;

        if (currIndex != index && values[currIndex] % 2 == 0) {

            result = currIndex;

        } else {

            if (this.values.length > currIndex + 1) {

                return getNextEvenIndex(++currIndex);
            }
        }

        return result;
    }

    private boolean hasNextEvenIndex() {

        boolean hasNextEvenIndex = false;

        int currIndex = index;

        if (getNextEvenIndex(currIndex) != index) {

            hasNextEvenIndex = true;
        }

        return hasNextEvenIndex;
    }

    @Override
    public Object next() {

        if (!this.hasNext()) {

            throw new NoSuchElementException();

        } else {

            index = getNextEvenIndex(index);

            return values[index];

        }
    }
}