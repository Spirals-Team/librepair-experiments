package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * AssociationTest.
 * @author Hincu Andrei (andreih1981@gmail.com) by 04.09.17;
 * @version $Id$
 * @since 0.1
 */
public class AssociationTest {
    /**
     * Когда первый массив короче второго.
     */
    @Test
    public void whenInFirstArrayAndSecondArrayThenOneArray() {
        int[] first = {2, 5, 7, 9};
        int[] second = {1, 3, 4, 6, 8, 10};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Association association = new Association();
        int[] ex = association.assotiationSortedArray(first, second);
        assertThat(result, is(ex));
    }

    /**
     * Когда первый длиннее второго.
     */
    @Test
    public void whenInFirstArrayAndSecondArrayThenOneArray2() {
        int[] first = {2, 5, 7, 9};
        int[] second = {1, 3, 4, 6, 8, 10};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Association association = new Association();
        int[] ex = association.assotiationSortedArray(second, first);
        assertThat(result, is(ex));
    }
    /**
     * Когда массивы одной длинны.
     */
    @Test
    public void whenInFirstArrayAndSecondArrayThenOneArray3() {
        int[] first = {2, 5, 7, 8};
        int[] second = {1, 3, 4, 6};
        int[] result = {1, 2, 3, 4, 5, 6, 7, 8};
        Association association = new Association();
        int[] ex = association.assotiationSortedArray(second, first);
        assertThat(result, is(ex));
    }
}
