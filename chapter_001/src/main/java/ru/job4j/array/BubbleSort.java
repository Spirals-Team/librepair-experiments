package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BubbleSort {

    /**
     * Метод сортировки массива целых чисел, используя алгоритм сортировки пузырьком.
     * @param array - массив для сортировки.
     * @return - отсортированный массив.
     */
    public int[] sort(int[] array) {
        int temp;
        for (int j = 0; j < array.length; j++) {
            for (int i = 0; i < array.length; i++) {
                if (i + 1 < array.length && array[i] > array[i + 1]) {
                    temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                }
            }
        }
        return array;
    };
}
