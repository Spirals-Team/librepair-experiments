package ru.job4j.array;

/**
 * Merge arrays.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class MergeArrays {
    /**
     * Merge two arrays.
     * @param leftArray - left array
     * @param rightArray - right array
     * @return joint array
     */
    public int[] merge(int[]leftArray, int[]rightArray) {
        int[] array = new int[leftArray.length + rightArray.length];
        int leftIndex = 0;
        int rightIndex = 0;

        for (int i = 0; i < array.length; i++) {

            if (leftIndex > leftArray.length - 1) {
                array[i] = rightArray[rightIndex++];
            } else if (rightIndex > rightArray.length - 1) {
                array[i] = leftArray[leftIndex++];
            } else if (leftArray[leftIndex] < rightArray[rightIndex]) {
                array[i] = leftArray[leftIndex++];
            } else {
                array[i] = rightArray[rightIndex++];
            }
        }
        return array;
    }
}
