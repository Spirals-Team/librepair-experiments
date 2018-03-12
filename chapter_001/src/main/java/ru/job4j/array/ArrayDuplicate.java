package ru.job4j.array;

import java.util.Arrays;

/**
 * Class ArrayDuplicate. Удаляем дубликаты из массива.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 18.09.2017
 */
public class ArrayDuplicate {
    /**
     * Method remove. Принимает на вход массив на выходе вернет массив без дубликатов.
     *
     * @param array массив
     * @return Сортированыый массив
     */
    public String[] remove(String[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (array[i].equals(array[j])) {
                    array[j] = array[length - j];
                    length--;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, length);
    }
}
