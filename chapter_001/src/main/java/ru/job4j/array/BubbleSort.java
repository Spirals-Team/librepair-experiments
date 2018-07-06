package ru.job4j.array;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class BubbleSort {
    public int[] sort(int[] array) {
        for (int j = array.length - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (array[i] > array[i + 1]) {
                    int rem = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = rem;
                }
            }
        }
        return array;
    }
}
