package ru.job4j.iterator;

import java.util.NoSuchElementException;

/**
 * Iterator over even numbers of an array.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class EvenNumbersIterator implements Iterator {

    private int[] ints;
    private int position = 0;

    public EvenNumbersIterator(int[] ints) {
        this.ints = ints;
    }

    @Override
    public Object next() {
        if (isArrayEmpty()) {
            throw new NoSuchElementException();
        }

        position = getIdxNextEvenNum(position);

        if (position < 0) {
            throw new NoSuchElementException();
        }

        int item = ints[position];
        position++;

        return item;
    }

    @Override
    public boolean hasNext() {
        return !isArrayEmpty() && getIdxNextEvenNum(position) >= 0;
    }

    private int getIdxNextEvenNum(int position) {
        for (int idx = position; idx < ints.length; idx++) {
            if (isEven(ints[idx])) {
                return idx;
            }
        }
        return -1;
    }

    private boolean isEven(int num) {
        return num % 2 == 0;
    }

    private boolean isArrayEmpty() {
        return ints == null || ints.length < 1;
    }
}
