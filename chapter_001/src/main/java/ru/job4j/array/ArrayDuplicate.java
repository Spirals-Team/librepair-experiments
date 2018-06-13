package ru.job4j.array;

import java.util.Arrays;

public class ArrayDuplicate {
    /**
     * Класс убрает все дубликаты строк из массива
     *
     * @param array - сортируемый массив
     * @return - возвращает отсортированный массив
     * @author Alexandar Vysotskiy
     * @version 1.0
     */

    public String[] remove(String[] array) {
        int whole = array.length;
        for (int out = 0; out < whole; out++) {
            for (int in = out + 1; in < whole; in++) {
                if (array[out].equals(array[in])) {
                    array[in] = array[whole - 1];
                    whole--;
                    in--;
                }
            }
        }
        return Arrays.copyOf(array, whole);
    }
}
