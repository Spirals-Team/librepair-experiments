package ru.job4j.array;

/**
 * Rotate array.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class RotateArray {
    /**
     * Array rotates clockwise.
     * @param array - array
     * @return rotated array
     */
    public int[][] rotate(int[][] array) {
        int size = array.length;
        for (int i = 0; i < size / 2; ++i) {
            for (int j = i; j < size - 1 - i; ++j) {
                int tmp = array[size - 1 - j][i];
                array[size - 1 - j][i] = array[size - 1 - i][size - 1 - j];
                array[size - 1 - i][size - 1 - j] = array[j][size - 1 - i];
                array[j][size - 1 - i] = array[i][j];
                array[i][j] = tmp;
            }
        }
        return array;
    }
}
