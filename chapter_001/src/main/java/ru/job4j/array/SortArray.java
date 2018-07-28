package ru.job4j.array;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SortArray {
    public int[] sort(int[] array1, int[] array2) {

        int size1 = array1.length;
        int size2 = array2.length;
        int[] array = new int[size1 + size2];

        int count1 = 0;
        int count2 = 0;

        int[] cur = null;
        int total = 0;

        for (int i = 0; i < size1 + size2; i++) {
            if (count1 == size1 | count2 == size2) {
                for (int y = total; y < cur.length; y++) {
                    array[i] = cur[y];
                    i++;
                }
            } else {
                if (array1[count1] < array2[count2]) {
                    array[i] = array1[count1];
                    count1++;
                    cur = array2;
                    total = count2;
                } else {
                    array[i] = array2[count2];
                    count2++;
                    cur = array1;
                    total = count1;
                }
            }
        }
        return array;
    }
}
