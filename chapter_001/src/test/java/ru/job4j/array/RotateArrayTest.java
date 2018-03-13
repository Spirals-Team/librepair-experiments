package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 *Test rotate array.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 11.10.2017
 */
public class RotateArrayTest {
    /**
     * Test rotating array.
     */
    @Test
    public void whenRotateTwoRowTwoColArrayThenRotatedArray() {

        int[][] testArray = {{1, 2}, {3, 4}};
        int[][] expected = {{3, 1}, {4, 2}};

        RotateArray rotateArray = new RotateArray();
        int[][]  result = rotateArray.rotate(testArray);
        assertThat(result, is(expected));
    }
    /**
     * Test rotating array.
     */
    @Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {

        int[][] testArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] expected = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};

        RotateArray rotateArray = new RotateArray();
        int[][]  result = rotateArray.rotate(testArray);
        assertThat(result, is(expected));
    }
}