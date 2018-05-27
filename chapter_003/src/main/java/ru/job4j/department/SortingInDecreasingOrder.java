package ru.job4j.department;

import java.util.Comparator;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SortingInDecreasingOrder implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        if (o1.compareTo(o2) == 0) {
            return 0;
        }
        char[] array1 = o1.toCharArray();
        char[] array2 = o2.toCharArray();
        for (int i = 0; i < array1.length; i++) {
            if (i >= array2.length) {
                return 1;
            }
            int indexCompare = Character.compare(array1[i], array2[i]);
            if (indexCompare != 0) {
                return indexCompare * -1;
            }
        }
        return array2.length > array1.length ? 1 : -1;
    }
}
