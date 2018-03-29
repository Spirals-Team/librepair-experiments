package ru.job4j.array;

import java.util.Arrays;

public class ArrayDuplicate {
    public String[] remove(String[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i].equals(array[j])) {
                    array[j] = array[n - 1];
                    n--;
                }
            }
        }
        return Arrays.copyOf(array, n);
    }
}
