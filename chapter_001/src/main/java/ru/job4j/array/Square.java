package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Square {

    /**
     * Метод заполнения массива числами в квадрате.
     * @param bound - размер массива.
     * @return заполненый массив.
     */
    public int[] calculate(int bound) {
        int[] rsl = new int[bound];
        int value = 1;
        for (int i = 0; i < bound; i++) {
            rsl[i] = value * value;
            value++;
        }
        // заполнить массив через цикл элементами от 1 до bound возведенные в квадрат
        return rsl;
    }
}
