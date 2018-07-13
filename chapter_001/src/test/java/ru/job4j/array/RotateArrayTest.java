package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RotateArrayTest {
    @Test
    public void whenRotateTwoRowTwoColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 2 на 2.
        int[][] arg=    {   {1,2},
                            {3,4}
        };
        int[][] result= {   {3,1},
                            {4,2}
        };
        RotateArray ss=new RotateArray();
        assertThat(ss.rotate(arg),is(result));

    }

    @Test
    public void whenRotateThreeRowThreeColArrayThenRotatedArray() {
        //напишите здесь тест, проверяющий поворот массива размером 3 на 3.
        int[][] arg=    {   {1,2,3},
                            {4,5,6},
                            {7,8,9}
        };
        int[][] result= {   {7,4,1},
                            {8,5,2},
                            {9,6,3}
        };
        RotateArray ss=new RotateArray();
        assertThat(ss.rotate(arg),is(result));

    }
}
