package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ArrayDuplicate {
    /**
     * Метод удаляет дубликаты в массиве
     * @param array массив строк
     * @return массив строк без дубликатов
     * @see ArrayDuplicate
     */
    public String[] remove(String[] array) {

        int cut = array.length;

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < cut; j++) {
                if (array[i].equals(array[j])) {
                    array[j] = array[j + 1];
                    cut--;
                }
            }
        }
        return Arrays.copyOf(array, cut);
    }
}
