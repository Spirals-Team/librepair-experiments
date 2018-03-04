package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * BubbleSort.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class BubbleSortTest {
    /**
     * Sort array with ten elements.
     */
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] array = new int[]{1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
        int[] expectArray = new int[]{0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        int[] resultArray = bubbleSort.sort(array);
        assertThat(resultArray, is(expectArray));
    }
}
