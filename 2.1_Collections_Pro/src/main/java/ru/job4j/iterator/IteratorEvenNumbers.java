package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *Класс IteratorEvenNumbers.
 *@author ifedorenko
 *@since 19.09.2017
 *@version 1
 */
public class IteratorEvenNumbers implements Iterator {
    /**
     * @param values array
     */
    private final int[] values;
    /**
     * @param count position in the array
     */
    private int count = 0;

    /**
     * Constructor.
     * @param values array.
     */
    public IteratorEvenNumbers(final int[] values) {
        this.values = values;
    }

    /**
     * Method hasNext.
     * @return result
     */
    @Override
    public boolean hasNext() {
        boolean result = false;
        for (int i = count; i < values.length; i++) {
            if (values[i] % 2 == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Method next.
     * @return object
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int result = 0;
        for (int i = count; i < values.length; i++) {
            count++;
            if (values[i] % 2 == 0) {
                result = values[i];
                break;
            }
        }
        return result;
    }
}
