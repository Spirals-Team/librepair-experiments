package ru.job4j.array;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class RotateArrayTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 15.09.2017
 */
public class RotateArrayTest {
    /**
     * Method whenRotateTwoRowTwoColArrayThenRotatedArray.
     */
    @Test
    public void whenRotateTwoRowTwoColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 2 на 2.
        RotateArray rotateArray = new RotateArray();
        int[][] in = {{1, 2}, {3, 4}};
        int[][] out = {{3, 1}, {4, 2}};
        assertThat(rotateArray.rotate(in), is(out));
    }

    /**
     * Method whenRotateThreeRowThreeColArrayThenRotatedArray.
     */
    @Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 3 на 3.
        RotateArray rotateArray = new RotateArray();
        int[][] in = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] out = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};

        assertThat(rotateArray.rotate(in), is(out));
    }

    /**
     * Method whenRotateFourRowFourColArrayThenRotatedArray.
     */
    @Test
    public void whenRotateFourRowFourColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 4 на 4.
        RotateArray rotateArray = new RotateArray();
        int[][] in = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] out = {{13, 9, 5, 1}, {14, 10, 6, 2}, {15, 11, 7, 3}, {16, 12, 8, 4}};

        assertThat(rotateArray.rotate(in), is(out));
    }
}
