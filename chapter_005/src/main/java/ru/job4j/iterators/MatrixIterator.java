package ru.job4j.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator implements Iterator<Integer> {

    private int[][] value;
    private int length = 0;
    private int limit = 0;
    private int row = 0;
    private int col = 0;

    public MatrixIterator(int[][] value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        if (this.length == 0) {
            for (int[] inner : this.value) {
                length += inner.length;
            }
        }
        return length > this.limit;
    }

    @Override
    public Integer next() {
        try {
            if (col >= this.value[row].length && row < this.value.length) {
                row++; col = 0;
            }
            this.limit++;
            return this.value[row][col++];

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }
}
