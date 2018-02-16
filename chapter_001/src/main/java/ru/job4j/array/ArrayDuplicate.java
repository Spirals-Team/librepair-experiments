package ru.job4j.array;
import java.util.Arrays;
/**
 * ArrayDuplicate.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {
    /**
     * Remove duplicates in array.
     * @param array - array
     * @return array without duplicates
     */
    public String[] remove(String[] array) {
        int count = array.length;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < count; j++) {
                if (array[i].equals(array[j])) {
                    array[j] = array[count - 1];
                    count--;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, count);
    }
}
