package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class BubbleSortTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 14.09.2017
 */
public class BubbleSortTest {
    /**
     * Method whenSortArrayWithTenElementsThenSortedArray.
     */
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] array = {1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
        int[] resault = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        assertThat(bubbleSort.sort(array), is(resault));
    }
}
