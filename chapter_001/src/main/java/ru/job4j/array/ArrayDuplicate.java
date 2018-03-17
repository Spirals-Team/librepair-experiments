package ru.job4j.array;

import java.util.Arrays;

/**
 * RotateArray.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {
    /**
     * Метод убирает дубликаты.
     * @param array входящий массив с дубликатами;
     * @return массив без дубликатов.
     */
    public String[] remove(String[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (array[i].equals(array[j])) {
                    array[j] = array[len - 1];
                    len--;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, len);
    }
    public int[]add(int[]a, int[]b) {
        int[]result = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int index = 0;
        do {
            if (i < a.length && j < b.length) {
                if (a[i] < b[j]) {
                    result[index++] = a[i];
                    i++;
                } else {
                    result[index++] = b[j];
                    j++;
                }
            } else if (i == a.length && j < b.length) {
                result[index++] = b[j];
                j++;
            } else if (j == b.length && i < a.length) {
                result[index++] = a[i];
                i++;
            }
        } while (index < result.length);
        return result;
    }
}
