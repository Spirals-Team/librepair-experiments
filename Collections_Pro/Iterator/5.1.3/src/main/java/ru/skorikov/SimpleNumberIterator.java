package ru.skorikov;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 27.09.17
 * @ version: java_kurs_standart
 * 5.1.3. Создать итератор простые числа.
 * Что бы не писать алгоритм проверяющий принадлежит ли число простым числам,
 * метод hasNext проверяет есть ли число в массиве простых чисел.
 */
public class SimpleNumberIterator implements Iterator {
    /**
     * Массив простых чисел.
     */
    private int[] simpeArray = new int[]
            {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
                    53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
                    127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197,
                    199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
                    283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379,
                    383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
                    467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571,
                    577, 587, 593, 599, 601};
    /**
     * Массив элементов.
     */
    private final int[] numbers;
    /**
     * Счетчик.
     */
    private int index = 0;

    /**
     * Коструктор.
     *
     * @param numbers массив элементов.
     */
    public SimpleNumberIterator(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean hasNext() {
        boolean isNext = false;
        for (int i = index; i < numbers.length;) {
            for (int j = 0; j < simpeArray.length; j++) {
                if (numbers[i] == simpeArray[j]) {
                    isNext = true;
                    index = i;
                    break;
                }
            }
            if (isNext) {
                break;
            } else {
                i++;
            }
        }
        return isNext;
    }

    @Override
    public Object next() {
        int returnInt = 0;
        if (hasNext()) {
            returnInt = numbers[index++];
        } else {
            throw new NoSuchElementException();
        }
        return returnInt;
    }

}
