package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *Класс IteratorDoubleArray.
 *@author ifedorenko
 *@since 19.09.2017
 *@version 1
 */
public class IteratorDoubleArray implements Iterator {
    /**
     * @param values double array
     */
    private final int[][] values;
    /**
     * @param row row in array
     */
    private int row = 0;
    /**
     * @param col column in array
     */
    private int col = 0;

    /**
     * Constructor.
     * @param values double array
     */
    public IteratorDoubleArray(int[][] values) {
        this.values = values;
    }

    /**
     * Method hasNext.
     * @return result
     */
    @Override
    public boolean hasNext() {
        return row < values.length;
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
        int result = values[row][col++];
        if (col == values[row].length) {
            row++;
            col = 0;
        }
        return result;
    }
}
