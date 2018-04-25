package ru.aserdyuchenko.iterator.task5_1_2;

import java.util.Iterator;

/**
 * @author anton.
 */
public class IteratorForEvenNumbers implements Iterator<Integer> {
    /**
     * @param array - исходный массив.
     */
    private int[] array;
    /**
     * @param index - индекс для массива.
     */
    private int index = 0;

    /**
     * Конструктор.
     * @param array - исходный массив с числами.
     */
    IteratorForEvenNumbers(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        boolean isEvenNumber = false;
        if (array.length > index) {
            int chekIndex = index;
            while (chekIndex < array.length) {
                int number = array[chekIndex++];
                if (number % 2 == 0) {
                    isEvenNumber = true;
                    break;
                }
            }
        }
        return isEvenNumber;
    }

    @Override
    public Integer next() {
        int number = array[index++];
        while (number % 2 != 0 && array.length > index) {
            number = array[index++];
        }
        return number;
    }
}
