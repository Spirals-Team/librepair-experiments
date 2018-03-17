package ru.job4j.array;
/**
 * RotateArray.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BubleSort {
    /**
     * Сортировка пузырьком.
     * @param array массив для сортировки.
     * @return - отсортированный масив.
     */
    public int[] sort(int[] array) {
        int[] a = array;
        boolean end = false;
        while (!end) {
            end = true;
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    end = false;
                }
            }
        }
        return a;
    }

    /**
     * Сортировка пузырьком.
     * @param array массив для сортировки.
     * @return - отсортированный масив.
     */
    public int[] bubleSort(int[] array) {
        int[] ar = array;
        for (int i = ar.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (ar[j] > ar[j + 1]) {
                    int temp = ar[j];
                    ar[j] = ar[j + 1];
                    ar[j + 1] = temp;

                }
            }
        }
        return ar;
    }
}
