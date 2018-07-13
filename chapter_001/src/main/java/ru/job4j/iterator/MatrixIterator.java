package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс определяет итератор, который ходит по 2х мерному массиву, заданному в конструкторе
 */
public class MatrixIterator implements Iterator<Integer> {
    // Внутреннее представление 2х мерного массива
    private int [][] inner;
    // Указатели на текущую позицию итератора
    private int rawpointer = 0;
    private int colpointer = 0;

    /**
     * Конструктор с входящим массивов
     * @param input входной массив
     */
    public MatrixIterator( int [][] input) {
        this.inner = input;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        int len = inner.length;
        if (rawpointer < len-1) {
            result=true;
        } else {
            if (rawpointer == len-1 && colpointer < inner[rawpointer].length ) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Integer next() {
        int len = inner.length;
        Integer result = null;
        if (rawpointer < len && colpointer < inner[rawpointer].length) {
            result = inner[rawpointer][colpointer++];
            if (colpointer == inner[rawpointer].length) {
                colpointer = 0;
                rawpointer ++;
            }
        } else {
            throw new NoSuchElementException();
        }
        return result;
    }
}
