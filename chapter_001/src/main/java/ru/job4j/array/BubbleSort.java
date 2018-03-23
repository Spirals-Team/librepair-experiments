package ru.job4j.array;
/**
 * BubbleSort.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class BubbleSort {
    /**
     * Bubble sort.
     * @param array - array for sorting
     * @return sorted array
     */
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
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
