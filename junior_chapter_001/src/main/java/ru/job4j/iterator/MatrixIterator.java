package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator implements Iterator {
    private int[][] values;
    private int i = 0;
    private int j = 0;

    public MatrixIterator(int[][] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return values.length > i && values[i].length > j;
    }

    @Override
    public Object next() {
        if (hasNext()) {
            Object x = values[i][j++];
            if (values[i].length == j) {
                i++;
                j = 0;
            }
            return x;
        } else {
            throw new NoSuchElementException("NoSuchElementException!");
        }
    }
}
