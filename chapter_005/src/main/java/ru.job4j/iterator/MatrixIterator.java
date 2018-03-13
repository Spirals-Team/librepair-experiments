package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator implements Iterator {

    private final int[][] values;
    private int indexX = 0;
    private int indexY = 0;

    public MatrixIterator(int[][] values) {

        this.values = values;

    }

    @Override
    public boolean hasNext() {

        return   nextX() || nextY();

    }

    boolean nextX() {

        return values.length > indexX;

    }

    boolean nextY() {

        int indX = indexX;

        if (!nextX()) {
            --indX;
        }

        return values[indX].length > indexY;

    }

    @Override
    public Object next() {

        if (!this.hasNext()) {
                throw new NoSuchElementException();
        }

        if (nextY()) {

            if (indexY + 1 == values[indexX].length) {

                return values[indexX++][indexY++];

            } else {

                return values[indexX][indexY++];
            }

        } else {

            indexY = 0;

            return values[indexX][indexY++];
        }
    }
}