package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BubbleSortTest {
    @Test
    public void arrBubbleSortTEST() {
        int[] arr1 = new int[]{5, 4, 2, 1, 3, 3, 8, 11};
        int[] expected = new int[]{1, 2, 3, 3, 4, 5, 8, 11};
        int[] result = new  BubbleSort().sort(arr1);
        assertThat(result, is(expected));
    }
    @Test
    public void arrBubbleSortTEST2() {
        int[] arr1 = new int[]{5, 4, 2, 1, 3, 3, 8, 11};
        int[] expected = new int[]{1, 2, 3, 3, 4, 5, 8, 11};
        int[] result = new  BubbleSort().sort2(arr1);
        assertThat(result, is(expected));
    }
}
