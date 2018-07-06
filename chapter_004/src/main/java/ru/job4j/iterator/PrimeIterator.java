package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class PrimeIterator implements Iterator {

    private final int[] values;
    private int cur = 0;

    public PrimeIterator(int[] values) {
        this.values = values;
    }

    private boolean checkPrimeNumber(int index) {
        boolean result = false;
        int current = values[index];
        int count = 0;
        for (int i = 1; i <= current; i++) {
            if (count == 3) {
                break;
            }
            if (current % i == 0) {
                count++;
            }
        }
        if (count == 2) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        int length = values.length;
        if (cur == length) {
            result = false;
        } else {
            if (checkPrimeNumber(cur)) {
                result = true;
            } else {
                while (cur < length - 1) {
                    if (checkPrimeNumber(++cur)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return values[cur++];
    }
}
