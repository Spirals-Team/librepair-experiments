package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс итератор, для многомерного массива.
 */
public class MatrixIterator implements Iterator  {

    private final int[][] values;
    private int out = 0;
    private int in = 0;

    public MatrixIterator(int[][] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return out < values.length && values.length != values[out].length;
    }

    @Override
    public Object next() {
        int result;
        if (out >= values.length) {
            throw new NoSuchElementException("no such element");
        } else {
        result = values[out][in++];
        if (in == values[out].length) {
            in = 0;
            out++;
        }
        }
        return result;
    }
}