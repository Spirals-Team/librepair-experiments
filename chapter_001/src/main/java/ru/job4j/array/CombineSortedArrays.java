package ru.job4j.array;
/**
 *  combine sorted arrays into one sorted array.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 13.10.2017
 */
public class CombineSortedArrays {
    /**
     * Method combineSortedArraysIntoSortedArray combining arrays int one array.
     * @param array1 array for combing.
     * @param array2 array for combing.
     * @return combined ordered array.
     */
    public int[] combineSortedArraysIntoSortedArray(int[] array1, int[] array2) {

        int maxIndArr1 = array1.length - 1;
        int maxIndArr2 = array2.length - 1;
        int currentIndArr1 = 0;
        int currentIndArr2 = 0;
        int maxInCombinedArr = array1.length + array2.length - 1;
        int[] combinedArray = new int[maxInCombinedArr + 1];

        for (int i = 0; i <= maxInCombinedArr; i++) {

            if (maxIndArr1 < currentIndArr1) {
                combinedArray[i] = array2[currentIndArr2];
                currentIndArr2 = currentIndArr2 + 1;
                continue;
            } else if (maxIndArr2 < currentIndArr2) {
                combinedArray[i] = array1[currentIndArr1];
                currentIndArr1 = currentIndArr1 + 1;
                continue;
            } else if (array1[currentIndArr1] < array2[currentIndArr2]) {
                combinedArray[i] = array1[currentIndArr1];
                currentIndArr1 = currentIndArr1 + 1;
                continue;
            } else {
                combinedArray[i] = array2[currentIndArr2];
                currentIndArr2 = currentIndArr2 + 1;
            }
        }
        return combinedArray;
    }
}
