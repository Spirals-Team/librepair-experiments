package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class BubbleSortTest {
    @Test
    public void whenSomeArrayThenSortArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] someArray = {5, 4, 3, 2, 1};
        int[] sortArray = {1, 2, 3, 4, 5};
        assertThat(bubbleSort.sort(someArray), is(sortArray));
    }
}
