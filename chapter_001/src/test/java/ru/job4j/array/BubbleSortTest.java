package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BubbleSortTest {
    @Test
    public void firstArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] result = {5, 1, 2, 7, 3};
        int[] expected = {1, 2, 3, 5, 7};
        bubbleSort.sort(result);
        assertThat(result, is(expected));
    }

    @Test
    public void secondArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] result = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        bubbleSort.sort(result);
        assertThat(result, is(expected));
    }
}
