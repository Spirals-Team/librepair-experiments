package ru.job4j.array;

/**
 * author Daniil Emelyanov
 * @version $id$
 * @since  15/03/2018
 */
public class Square {
    /**
     * Метод заполнения массива длинной bound числами от 1 до bound возведенных в квадрат.
     *
     * @param bound длина массива
     * @return заполненный массив
     */
    public int[] calculate(int bound) {
        int[] result = new int[bound];
        for (int index = 0; index < bound; index++) {
            result[index] = index * index;
        }
        return result;
    }
}