package ru.job4j.iterator;

import java.util.NoSuchElementException;

/**
 * Iterator over prime numbers of an array.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class PrimeIterator implements Iterator {

    private int[] ints;
    private int position = 0;

    public PrimeIterator(int[] ints) {
        this.ints = ints;
    }

    @Override
    public Object next() {
        if (isArrayEmpty()) {
            throw new NoSuchElementException();
        }

        position = getIdxNextPrimeNum(position);

        if (position < 0) {
            throw new NoSuchElementException();
        }

        int item = ints[position];
        position++;

        return item;
    }

    @Override
    public boolean hasNext() {
        return !isArrayEmpty() && getIdxNextPrimeNum(position) >= 0;
    }

    private int getIdxNextPrimeNum(int position) {
        for (int idx = position; idx < ints.length; idx++) {
            if (isPrime(ints[idx])) {
                return idx;
            }
        }
        return -1;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }

        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }

            if ((i == num) || (i > Math.sqrt(num))) {
                return true;
            }
        }
        return true;
    }

    private boolean isArrayEmpty() {
        return ints == null || ints.length < 1;
    }
}
