package ru.job4j.array;

import static java.lang.Math.*;

/**
 * @author  Alexander Kaleganov
 * @version Array V 1.0
 * @since 01.02.18
 * программа будет заполнять массив числа возведенные в квадрат
 *  в 16 ой строке делаем явное приведение типов, т.к. данный метод работает с double
 */
public class Square {
    public int[]  calculate(int bound) {
        int[] rsl = new int[bound];
        for (int i = 0; i < bound; i++) {
            rsl[i] = (int) pow((i + 1), 2);
        } return rsl;
    }
}
