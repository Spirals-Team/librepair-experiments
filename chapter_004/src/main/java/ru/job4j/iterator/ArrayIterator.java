package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator {

    private final int[][] values;
    private int  lengthArray = 0;
    private int index = 0;

    public ArrayIterator(final int[][] values) {
        this.values = values;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                lengthArray++;
            }
        }
    }



    @Override
    public boolean hasNext() {
        return index < lengthArray;
    }

    @Override
    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
        Object result = null;
        int indexA = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                indexA++;
                if (indexA == index) {
                     result = (int) values[i][j];
                }
            }
        }
        return result;
    }
}

