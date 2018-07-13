package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс определяет итератор, который ходит по четным числам в массиве, заданном в конструкторе
 */
public class EvenIterator implements Iterator {
    private int [] inner;
    // Указатель на текущую позицию итератора
    private int pointer = 0;

    /**
     * Конструктор с входящим массивов
     * @param numbers входной массив
     */
    public EvenIterator (final int[] numbers) {
        this.inner = numbers;
    }

    private int findNextEven( int point) {
        int result = -1;
        for (int i = point; i < inner.length; i++) {
            if ( inner[i] % 2 == 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        return findNextEven(pointer) > -1;
    }

    @Override
    public Integer next() {
        int result = findNextEven(pointer);
        if (hasNext()){
            pointer = result + 1;
            result = inner[result];
        } else {
            throw new NoSuchElementException();
        }
        return result;
    }
}
