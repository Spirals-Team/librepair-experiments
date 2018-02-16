package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Rotate array.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class RotateArrayTest {
    /**
     * Rotate two row two col array.
     */
    @Test
    public void whenRotateTwoRowTwoColArrayThenRotatedArray() {
        int[][] array = {{1, 2}, {3, 4}};
        RotateArray rotateArray = new RotateArray();
        int[][] resultArray = rotateArray.rotate(array);
        int[][] expectArray = {{3, 1}, {4, 2}};
        assertThat(resultArray, is(expectArray));
    }
    /**
     * Rotate three row three col array.
     */
    @Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RotateArray rotateArray = new RotateArray();
        int[][] resultArray = rotateArray.rotate(array);
        int[][] expectArray = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};
        assertThat(resultArray, is(expectArray));
    }
}
