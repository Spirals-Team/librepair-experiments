package ru.job4j.comparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListCompare implements Comparator<String> {

    /**
     * Метод поэлементно сравнивает два строки сначала по длине символов в каждой, если равны, то в лексикографическом порядке
     * @param - две строки
     */
    @Override
    public int compare(String left, String right) {
        int len1 = left.length();
        int len2 = right.length();
        int lim = Math.min(len1, len2);
        char[] v1 = left.toCharArray();
        char[] v2 = right.toCharArray();

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }
}
