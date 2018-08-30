package ru.job4j.iterator;

import java.util.NoSuchElementException;

/**
 * An iterator over a two-dimensional array (matrix).
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class MatrixIterator implements Iterator {

    private int[][] items;
    private int row = 0;
    private int column = 0;

    public MatrixIterator(int[][] items) {
        this.items = items;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        int item = items[row][column];
        setPosition();
        return item;
    }

    @Override
    public boolean hasNext() {
        return !isArrayEmpty() && !isOutOfArray();
    }

    private void setPosition() {
        if (column < items[row].length - 1) {
            column++;
        } else {
            row++;
            column = 0;
        }
    }

    private boolean isArrayEmpty() {
        return items == null || items.length < 1;
    }

    private boolean isOutOfArray() {
        return !(row < items.length && column < items[row].length);
    }
}
