package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class PrimeIterator implements Iterator {

    private final int[] values;
    private int index = 0;

    public PrimeIterator(final int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return hasNextPrimeIndex();
    }

    private boolean valueIsPrime(int nN) {

        if (nN < 2) {
            return false;
        }

        for (int i = 2; i * i <= nN; i++) {

            if (nN % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int getNextPrimeIndex(int currIndex) {

        int result = index;

        if (currIndex != index && valueIsPrime(values[currIndex])) {

            result = currIndex;

        } else {

            if (this.values.length > currIndex + 1) {

                return getNextPrimeIndex(++currIndex);
            }
        }

        return result;
    }

    private boolean hasNextPrimeIndex() {

        boolean hasNextPrimeIndex = false;

        int currIndex = index;

        if (getNextPrimeIndex(currIndex) != index) {

            hasNextPrimeIndex = true;
        }

        return hasNextPrimeIndex;
    }

    @Override
    public Object next() {

        if (!this.hasNext()) {

            throw new NoSuchElementException();

        } else {

            index = getNextPrimeIndex(index);

            return values[index];

        }
    }
}