package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Turn {

    public int[] back(int[] array) {
        int temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    };
}
