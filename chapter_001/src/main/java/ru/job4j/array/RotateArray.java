package ru.job4j.array;
/**
 * Rotate array.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 11.10.2017
 */
public class RotateArray {

    /**
     * Method back Rotated array.
     * @param array array to by rotated.
     * @return rotated array.
     */
    public int[][] rotate(int[][] array) {

        int[][] rotatedArray = new int[array.length][array.length];

        for (int i = 0; i <= array.length - 1; i++) {

            for (int k = 0; k <= array.length - 1; k++) {

                rotatedArray[k][i] = array[array.length - 1 - i][k];

            }
        }

        return rotatedArray;

    }

}
