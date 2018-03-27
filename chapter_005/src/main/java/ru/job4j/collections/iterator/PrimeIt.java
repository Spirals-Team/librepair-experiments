package ru.job4j.collections.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 30.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class PrimeIt implements Iterator {
    /**
     * Массив значений.
     */
    private final int[]numbers;
    /**
     * Позиция в массиве.
     */
    private int index = 0;

    /**
     * Конструктор класса.
     * @param numbers массив.
     */
    public PrimeIt(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Метод проверяет есть ли в масиве еще простые числа.
     * @return true or false.
     */
    @Override
    public boolean hasNext() {
        boolean found = false;
            for (int i = index; i < numbers.length; i++) {
                for (int j = 1; j <= numbers[i];) {
                    if (numbers[i] == 0) {
                        break;
                    }
                    if (numbers[i] == j) {
                        found = true;
                        index = i;
                        break;
                    }
                    j++;
                    if (numbers[i] % j == 0 && numbers[i] != j) {
                        break;
                    } else if (numbers[i] % j == 0 && numbers[i] == j) {
                        found = true;
                        index = i;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        return found;
    }

    /**
     * Метод возвращает текущее значение и переводит каретку на следующее значение.
     * @return значение из массива.
     */
    @Override
    public Object next() {
        int value = 0;
        if (hasNext()) {
            value = numbers[index];
            index++;
        } else {
            throw new NoSuchElementException();
        }
        return value;
    }
}
