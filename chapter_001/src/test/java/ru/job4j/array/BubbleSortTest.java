package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test array sorting by the method of permutation.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 11.10.2017
 */
public class BubbleSortTest {
    /**
     * Test array sorting by the method of permutation.
     */
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        int[] testArray = {1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
        BubbleSort bubbleSort = new BubbleSort();
        int[]  result = bubbleSort.sort(testArray);
        int[] expected = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        assertThat(result, is(expected));
    }

    /**
     * Test array sorting by the method of permutation.
     */
    @Test
    public void whenSortArrayWithFourElementsThenSortedArray() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        int[] testArray = {1, 5, 4, 2};
        BubbleSort bubbleSort = new BubbleSort();
        int[]  result = bubbleSort.sort(testArray);
        int[] expected = {1, 2, 4, 5};
        assertThat(result, is(expected));
    }
}