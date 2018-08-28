package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class CheckSortedArrayTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 24.09.2017
 */
public class CheckSortedArrayTest {
    /**
     * Method checkArrayGood.
     */
    @Test
    public void checkArrayGood() {
        CheckSortedArray checkSortedArray = new CheckSortedArray();
        int[] array = {0, 2, 4, 6, 9, 10, 13, 14, 18, 19};
        assertThat(checkSortedArray.checkSortedArray(array), is(true));
    }

    /**
     * Method checkArrayFail.
     */
    @Test
    public void checkArrayFail() {
        CheckSortedArray checkSortedArray = new CheckSortedArray();
        int[] array = {0, 89, 4, 65, 9, 20, 13, 14, 19, 18};
        assertThat(checkSortedArray.checkSortedArray(array), is(false));
    }
}
