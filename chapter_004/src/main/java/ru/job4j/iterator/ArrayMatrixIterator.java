package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayMatrixIterator implements Iterator {
    private final int[][] matrix;
    private int positionX = 0;
    private int positionY = 0;
    private int size = 0;
    private int result = 0;
    private boolean lastIteration = false;


    public ArrayMatrixIterator(int[][] matrix) {
        this.matrix = matrix;
    }

    public Integer next() throws NoSuchElementException {
        for (int i = positionX; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result = matrix[positionX][positionY];
                if (positionY != matrix[i].length) {
                    size = matrix[i].length;
                    positionY++;
                    break;
                }
            }
            break;
        }
        if (lastIteration) {
            throw new NoSuchElementException();
        }
        if (positionY == size) {
            positionY = 0;
            positionX++;
            if (positionX == matrix.length)
                lastIteration = true;
        }
        return result;
    }

    public boolean hasNext() {
        return positionX < matrix.length && positionY >= 0;
    }
}
