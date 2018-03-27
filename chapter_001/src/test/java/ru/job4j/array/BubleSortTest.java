package ru.job4j.array;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test.
 *
 * @author Andrei Hincu (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BubleSortTest {
    /**
     * Тест.
     */
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        int[] array = {1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
        BubleSort bubleSort = new BubleSort();
        int[] resultArray = bubleSort.sort(array);
        int[] ex = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        assertThat(resultArray, is(ex));
    }
    /**
     * Тест.
     */
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray2() {
        int[] array = {1, 5, 4, 2, 3, 1, 7, 8, 0, 5};
        BubleSort bubleSort = new BubleSort();
        int[] resultArray = bubleSort.bubleSort(array);
        int[] ex = {0, 1, 1, 2, 3, 4, 5, 5, 7, 8};
        assertThat(resultArray, is(ex));
    }
}
