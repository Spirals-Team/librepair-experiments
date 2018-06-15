package ru.job4j.array;
/**
 * 	Дан массив надо проверить что он отсортирован.
 */

public class ArraySortCheck {
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i + 1] < array[i] || array[i + 1] > array[i]) {
                break;
            }
        }
        return array;
    }
}
