package ru.job4j.array;
/**
 *  sorting an array by the method of permutation.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 11.10.2017
 */
public class BubbleSort {

    /**
     * Method sort sorting an array by the method of permutation.
     * @param array array to by sorted.
     * @return sorted array.
     */
    public int[] sort(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        return array;
    }
}
