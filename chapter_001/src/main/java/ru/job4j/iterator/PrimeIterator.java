package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс определяет итератор, который ходит по простым числам в массиве, заданном в конструкторе
 */
public class PrimeIterator implements Iterator {
    private int [] inner;
    // Указатель на текущую позицию итератора
    private int pointer = 0;

    /**
     * Конструктор с входящим массивом
     * @param numbers входной массив
     */
    public PrimeIterator (final int[] numbers) {
        this.inner = numbers;
    }

    private int findNextPrime( int point) {
        int result = -1;
        for (int i = point; i < inner.length; i++) {
            if ( primenumber(inner[i])) {
                result = i;
                break;
            }
        }
        return result;
    }
    private boolean primenumber (int number) {
        boolean result = number > 1;
        for (int i = 2; i < number; i++) {
            if (number%i == 0) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        return findNextPrime(pointer) > -1;
    }

    @Override
    public Integer next() {
        int result = findNextPrime(pointer);
        if (hasNext()){
            pointer = result + 1;
            result = inner[result];
        } else {
            throw new NoSuchElementException();
        }
        return result;
    }
}
