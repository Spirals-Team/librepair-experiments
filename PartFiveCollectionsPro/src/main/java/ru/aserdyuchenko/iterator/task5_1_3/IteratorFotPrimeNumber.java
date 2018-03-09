package ru.aserdyuchenko.iterator.task5_1_3;

import java.util.Iterator;

/**
 * @author anton.
 * @version 1.0.
 */
public class IteratorFotPrimeNumber implements Iterator<Integer> {
    /**
     * @param array - array with numbers.
     */
    private int[] array;
    /**
     * @param index - index for array.
     */
    private int index;

    /**
     * Constructor.
     * @param array - array with numbers.
     */
    IteratorFotPrimeNumber(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        boolean isPrimeNumber = false;
        int chekIndex = index;
        if (array.length > chekIndex) {
            while (array.length > chekIndex && !isPrimeNumber) {
                int number = array[chekIndex++];
                isPrimeNumber = true;
                for (int i = 2; i < number; i++) {
                    if (number % i == 0) {
                        isPrimeNumber = false;
                        break;
                    }
                }
            }
        }
        return isPrimeNumber;
    }

    @Override
    public Integer next() {
        int number = array[index++];
        boolean isPrimeNumber = false;
        while (array.length > index && !isPrimeNumber) {
            number = array[index++];
            isPrimeNumber = true;
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    isPrimeNumber = false;
                    break;
                }
            }
        }
        return number;
    }
}
