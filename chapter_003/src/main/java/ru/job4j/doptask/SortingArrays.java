package ru.job4j.doptask;

import java.util.Comparator;
import java.util.List;

/**
 * Класс для сортировки массивов.
 */
public class SortingArrays {
    /**
     * Сортировка списка массивов по возрастанию.
     *
     * @param inArray список массивов.
     * @return отсортированный список массивов по возрастанию.
     */
    public List<int[]> sortingTop(List<int[]> inArray) {
        inArray.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int result = 0;
                int size = o1.length < o2.length ? o1.length : o2.length;
                for (int i = 0; i < size; i++) {
                    if (o1[i] != o2[i]) {
                        result = o1[i] > o2[i] ? 1 : -1;
                        break;
                    }
                }
                return result != 0 ? result : 1;
            }
        });
        return inArray;
    }

    /**
     * Сортировка списка массивов по убыванию.
     *
     * @param inArray список массивов.
     * @return отсортированный список массивов по убыванию.
     */
    public List<int[]> sortingDown(List<int[]> inArray) {
        inArray.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int result = 0;
                int size = o1.length < o2.length ? o1.length : o2.length;
                for (int i = 0; i < size; i++) {
                    if (o1[i] != o2[i]) {
                        result = o1[i] < o2[i] ? 1 : -1;
                        break;
                    }
                }
                return result != 0 ? result : 1;
            }
        });
        return inArray;
    }
}
