package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BubbleSortTest {

    /**
     * Тест сортировки.
     */
    @Test
    public void sort() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] testArray = {5, 1, 2, 7, 3};
        int[] expectArray = {1, 2, 3, 5, 7};
        testArray = bubbleSort.sort(testArray);
        assertThat(testArray, is(expectArray));
    }
}