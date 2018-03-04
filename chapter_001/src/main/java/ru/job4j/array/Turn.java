package ru.job4j.array;
/**
 * Turn.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Turn {
    /**
     * Inverts an array.
     * @param array - array for invert
     * @return inverted array
     */
    public int[] back(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
        return array;
    }
}
