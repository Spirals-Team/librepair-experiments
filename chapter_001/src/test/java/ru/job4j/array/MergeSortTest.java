package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class MergeSortTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 24.09.2017
 */
public class MergeSortTest {
    /**
     * Method sortArray.
     */
    @Test
    public void sortArray() {
        MergeSort mergeSort = new MergeSort();
        int[] arrayA = {0, 2, 4, 6, 9, 10, 13, 14, 17, 18};
        int[] arrayB = {1, 3, 5, 7, 8, 11, 12, 15, 16, 19};
        int[] resault = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        assertThat(mergeSort.mergeSort(arrayA, arrayB), is(resault));
    }
    /**
     * Method theFirstArrayIsClean.
     */
    @Test
    public void theFirstArrayIsClean() {
        MergeSort mergeSort = new MergeSort();
        int[] arrayA = {};
        int[] arrayB = {1, 3, 5, 7, 8, 11, 12, 15, 16, 19};
        int[] resault = {1, 3, 5, 7, 8, 11, 12, 15, 16, 19};
        assertThat(mergeSort.mergeSort(arrayA, arrayB), is(resault));
    }
    /**
     * Method theSecondArrayIsClean.
     */
    @Test
    public void theSecondArrayIsClean() {
        MergeSort mergeSort = new MergeSort();
        int[] arrayA = {1, 3, 5, 7, 8, 11, 12, 15, 16, 19};
        int[] arrayB = {};
        int[] resault = {1, 3, 5, 7, 8, 11, 12, 15, 16, 19};
        assertThat(mergeSort.mergeSort(arrayA, arrayB), is(resault));
    }
    /**
     * Method arraysOfDifferentLengths.
     */
    @Test
    public void arraysOfDifferentLengths() {
        MergeSort mergeSort = new MergeSort();
        int[] arrayA = {0, 2, 4, 6, 9, 10, 13, 14, 17, 18, 20, 22, 23};
        int[] arrayB = {1, 3, 5, 7, 8, 11, 12, 15, 16, 19};
        int[] resault = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23};
        assertThat(mergeSort.mergeSort(arrayA, arrayB), is(resault));
    }
}
