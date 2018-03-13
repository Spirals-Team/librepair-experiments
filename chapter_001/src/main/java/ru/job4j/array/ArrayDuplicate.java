package ru.job4j.array;

import java.util.Arrays;
/**
 * Deleting duplicates in an array.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 11.10.2017
 */
public class ArrayDuplicate {

    /**
     * The method removes duplicate values from an array.
     * @param array is array to by rotated.
     * @return array with deleted duplicate values.
     */
    public String[] remove(String[] array) {

        int unique = array.length;

        for (int out = 0; out < unique; out++) {
            for (int in = 1; in < unique; in++) {
                if (array[in].equals(array[out]) && in != out) {
                    String duplicateValue = array[in];
                    array[in] = array[unique - 1];
                    array[unique - 1] = duplicateValue;
                    unique--;
                    in--;
                }
            }
        }
        array = Arrays.copyOf(array, unique);
        return array;
    }
}
