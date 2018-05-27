package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class ArrayDuplicate {

    /**
     *  Метод убирает все дубликаты строк из массива;
     * @param array входной массив.
     * @return массив с удаленными дубликатами.
     */
    public String[] remove(String[] array) {
        int index = 0;
        for (int i = 0; i < array.length - index; i++) {
            for (int j = i; j < array.length; j++) {
                if (i != j && array[i].equals(array[j])) {
                    array[j] = array[array.length - index - 1];
                    array[array.length - index - 1] = "";
                    index++;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, array.length - index);
    };
}
