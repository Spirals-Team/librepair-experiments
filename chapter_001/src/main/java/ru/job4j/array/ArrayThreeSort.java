package ru.job4j.array;

/**
 * Даны 2 массива оба отсортированы по возрастанию необходимо добавить третий массив.
 * что бы в третьем массиве все элементы были отсортированы по возрастанию,
 */

public class ArrayThreeSort {
    public int[] threeIsSort(int[] array1, int[] array2) {
        int[] array3 = new int[array1.length + array2.length];
        int array1Index = 0;
        int array2Index = 0;
        int array3Index = 0;

        while (array1Index < array1.length && array2Index < array2.length) {
            array3[array3Index++] = array1[array1Index] < array2[array2Index] ? array1[array1Index++] : array2[array2Index++];
        }
        if (array1Index < array1.length) {
            System.arraycopy(array1, array1Index, array3, array3Index, array1.length - array1Index);
        } else  if (array2Index < array2.length) {
            System.arraycopy(array2, array2Index, array3, array3Index, array2.length - array2Index);
        }
        return array3;
    }
    }
