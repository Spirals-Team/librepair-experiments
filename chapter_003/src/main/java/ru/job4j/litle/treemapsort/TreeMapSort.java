package ru.job4j.litle.treemapsort;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 02.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class TreeMapSort {
    /**
     * Мап отсортивованная по возрастанию.
     */
    private TreeMap<int[], Object> map = new TreeMap<>(new Comparator<int[]>() {
        @Override
        public int compare(int[] ints, int[] t1) {
            int temp = ints.length - t1.length;
            if (temp == 0) {
                for (int i = 0; i < ints.length;) {
                    temp = ints[i] - t1[i];
                    if (temp == 0) {
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (temp > 0) {
                for (int i = 0; i < t1.length; i++) {
                    temp = ints[i] - t1[i];
                }
                if (temp == 0) {
                    temp = ints.length - t1.length;
                }
            }
            return temp;
        }
    });

    /**
     * Метод для добовления новых элементов в map.
     * @param m новый ключь.
     * @return мап с добавленным элементом.
     */
    public TreeMap<int[], Object> sortByIncrease(int[]m) {
        map.put(m, null);
        return map;
    }

    /**
     * Метод для добавления новых элементов в мап1.
     * @param m новый ключь.
     * @return мап с добавленным элементом.
     */
    public TreeMap<int[], Object> sortByWaning(int[]m) {
        map1.put(m, null);
        return map1;
    }

    /**
     * Мап отсортированная по убыванию.
     */
    private TreeMap<int[], Object> map1 = new TreeMap<>(new Comparator<int[]>() {
        @Override
        public int compare(int[] o, int[] o1) {
            int temp = o.length - o1.length;
            if (temp == 0) {
                for (int i = 0; i < o.length;) {
                    temp = o1[i] - o[i];
                    if (temp == 0) {
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (temp > 0) {
                for (int i = 0; i < o1.length; i++) {
                    temp = o1[i] - o[i];
                }
                if (temp == 0) {
                    temp = o1.length - o.length;
                }
            } else {
                for (int i  = 0; i < o.length; i++) {
                    temp = o1[i] - o[i];
                }
                if (temp == 0) {
                    temp = o1.length - o.length;
                }
            }
            return temp;
        }
    });
}