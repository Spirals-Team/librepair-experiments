package ru.job4j.array;
/**
 * Reverse array.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 10.10.2017
 */
public class Turn {

    /**
     * Method back Reverse array.
     * @param array array to by reversed.
     * @return reversed array.
     */
    public int[] back(int[] array) {

        int arraylength = array.length;

        for (int i = 0; i <= ((arraylength % 2 != 1) ? (arraylength - 1) / 2 : (arraylength - 2) / 2); i++) {
            int rightValue = array[arraylength - 1 - i];
            int leftValue = array[i];
            array[i] = rightValue;
            array[arraylength - 1 - i] = leftValue;
        }
        return array;
    }
}
