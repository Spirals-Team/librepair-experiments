package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *Класс IteratorPrimeNumbers.
 *@author ifedorenko
 *@since 19.09.2017
 *@version 1
 */
public class IteratorPrimeNumbers implements Iterator {
    /**
     * @param values array
     */
    private final int[] values;
    /**
     * @param count value for controlling return
     */
    private int count = 0;

    /**
     * Constructor.
     *
     * @param values array.
     */
    public IteratorPrimeNumbers(final int[] values) {
        this.values = values;
    }

    /**
     * Method hasNext.
     *
     * @return result
     */
    @Override
    public boolean hasNext() {
        boolean result = false;
        for (int i = count; i < values.length; i++) {
            int temp = values[i];
            result = checkPrime(temp);
            if (result) {
                break;
            } else {
                continue;
            }
        }
        return result;
    }

    /**
     * Method next.
     *
     * @return object
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int result = 0;
        for (int i = count; i < values.length; i++) {
            result = values[i];
            count++;
            boolean temp = checkPrime(result);
            if (temp) {
                break;
            } else {
                continue;
            }
        }
        return result;
    }

    /**
     * Check prime.
     * @param value value from array
     * @return result
     */
    private boolean checkPrime(int value) {
        boolean result = true;
        if (value == 0 || value == 1 || (value % 2 == 0 && value != 2)) {
            result = false;
        } else {
            for (int i = 3; i < value / 2; i++) {
                if (value % i == 0) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}


