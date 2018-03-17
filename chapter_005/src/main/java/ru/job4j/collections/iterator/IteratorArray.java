package ru.job4j.collections.iterator;

import java.util.Iterator;

/**
 *Итератор для двумерного массива .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 30.09.17;
 * @version $Id$
 * @since 0.1
 */
public class IteratorArray implements Iterator {
    /**
     * Массив.
     */
    private final int[][] array;
    /**
     * Строки.
     */
    private int rows = 0;
    /**
     * Столбцы.
     */
    private int colums = 0;

    /**
     * Конструктор.
     * @param array массив.
     */
    public IteratorArray(final int[][] array) {
        this.array = array;
    }

    /**
     * Метод проверяем можно ли двигаться дальше по массиву исходя из текущей позиции.
     * @return true or false.
     */
    @Override
    public boolean hasNext() {
        return array.length > rows && array[rows].length > colums;
    }

    /**
     * Метод для прохождения массива.
     * @return зачение исходя из текущего положения и сдвигает каретку на 1 вперед.
     */
    @Override
    public Object next() {
        int value = array[rows][colums];
        colums++;
        if (colums >= array[0].length) {
            colums = 0;
            rows++;
        }
        return value;
    }
}
