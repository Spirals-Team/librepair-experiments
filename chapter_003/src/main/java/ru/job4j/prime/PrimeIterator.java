package ru.job4j.prime;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * class PrimeIterator
 *
 * @author Aleksandar Visotskiy
 * @version 2.0
 * @since 22.05.18
 */
public class PrimeIterator implements Iterator {
    /**
     * This in array.
     */
    private int[] array;

    /**
     * This is index for array;
     */
    private int index;

    /**
     * This is a constructor.
     *
     * @param array
     */
    public PrimeIterator(final int[] array) {
        this.array = array;
    }

    /**
     * This is hasNext
     *
     * @return true if there is next object, order true.
     */
    @Override
    public boolean hasNext() {
        boolean result = false;
        while (this.index < array.length) {
            if (isPrime(this.array[this.index])) {
                result = true;
                break;
            }
            this.index++;
        }
        return result;
    }

    /**
     * This is next method.
     *
     * @return next object.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return array[index++];
    }

    /**
     * This is an auxiliary method
     *
     * @param num - verifiable number.
     * @return true if verifiable number is isPrime.
     */
    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}