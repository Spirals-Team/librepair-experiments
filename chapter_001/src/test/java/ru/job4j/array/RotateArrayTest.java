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
public class RotateArrayTest {
    /**
     * Test 2 *2.
     */
    @Test
    public void whenRotateTwoRowTwoColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 2 на 2.
        RotateArray rotateArray = new RotateArray();
        int[][] array = {
                {1, 2},
                {3, 4}
        };
        int[][] rotateTest = rotateArray.rotate(array);
        int[][] ex = {
                {3, 1},
                {4, 2}
        };
        assertThat(rotateTest, is(ex));
    }

    /**
     * Test 3 * 3.
     */
    @Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 3 на 3.
        RotateArray rotateArray = new RotateArray();
        int[][] array = {{1, 2, 3},
                         {4, 5, 6},
                         {7, 8, 9} };
        int[][] rezultRotate = rotateArray.rotate(array);
        int[][] ex = {{7, 4, 1},
                      {8, 5, 2},
                      {9, 6, 3} };
        assertThat(rezultRotate, is(ex));
    }
}