package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test combining two sorted arrays into one sorted array.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 13.10.2017
 */
public class CombineSortedArraysTest {
    /**
     * Test combining two sorted arrays into one sorted array.
     */
    @Test
    public void whenCombineSortedArraysFirstArrayShoterTheSecond() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        int[] testArray1 = {0, 1, 1, 5, 9};
        int[] testArray2 = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        CombineSortedArrays combineSortedArrays = new CombineSortedArrays();
        int[]  result = combineSortedArrays.combineSortedArraysIntoSortedArray(testArray1, testArray2);
        int[] expected = {0, 0, 1, 1, 1, 1, 2, 3, 4, 5, 5, 5, 7, 8, 9};
        assertThat(result, is(expected));
    }
    /**
     * Test combining two sorted arrays into one sorted array.
     */
    @Test
    public void whenCombineSortedArraysFirstArrayLongerTheSecond() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        int[] testArray1 = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        int[] testArray2 = {0, 1, 1, 5, 9};
        CombineSortedArrays combineSortedArrays = new CombineSortedArrays();
        int[]  result = combineSortedArrays.combineSortedArraysIntoSortedArray(testArray1, testArray2);
        int[] expected = {0, 0, 1, 1, 1, 1, 2, 3, 4, 5, 5, 5, 7, 8, 9};
        assertThat(result, is(expected));
    }
    /**
     * Test combining two sorted arrays into one sorted array.
     */
    @Test
    public void whenCombineSortedArraysFirstArrayTheSameLengthAsSecond() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        int[] testArray1 = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        int[] testArray2 = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        CombineSortedArrays combineSortedArrays = new CombineSortedArrays();
        int[]  result = combineSortedArrays.combineSortedArraysIntoSortedArray(testArray1, testArray2);
        int[] expected = {0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 5, 5, 7, 7, 8, 8};
        assertThat(result, is(expected));
    }
    /**
     * Test combining two sorted arrays into one sorted array.
     */
    @Test
    public void whenCombineSortedArrays() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        int[] testArray1 = {0, 8};
        int[] testArray2 = {0, 88, 99};
        CombineSortedArrays combineSortedArrays = new CombineSortedArrays();
        int[]  result = combineSortedArrays.combineSortedArraysIntoSortedArray(testArray1, testArray2);
        int[] expected = {0, 0, 8, 88, 99};
        assertThat(result, is(expected));
    }
}
