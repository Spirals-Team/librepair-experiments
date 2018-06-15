package ru.job4j.array;

public class BubbleSort {
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int out = array.length - 1; out > i; out--) {
                if (array[out - 1] > array[out]) {
                    int temp = array[out - 1];
                    array[out - 1] = array[out];
                    array[out] = temp;
                }
            }
        }
        return array;
    }
}

