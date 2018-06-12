package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SortArrayTest {

    @Test
    public void whenTwoSortArraysThenNewSortArray() {
        SortArray sortArray = new SortArray();
        int[] firstArray = {1, 4, 20, 100};
        int[] secondArray = {1, 3, 15, 19, 55, 1000};
        int[] finalArray = {1, 1, 3, 4, 15, 19, 20, 55, 100, 1000};
        assertThat(sortArray.sort(firstArray, secondArray), is(finalArray));
    }
}
