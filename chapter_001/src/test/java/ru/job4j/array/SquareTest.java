package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SquareTest {
    @Test
    public void testirovanieArraySquareFORi() {
        int[] excepted = new int[]{1, 4, 9};
        int[] intarray = new Square().calculate(3);
        assertThat(intarray, is(excepted));

    }
//    @Test
//    public void testirovanieArraySquareFOReach() {
//        int[] excepted = new int[]{1, 4, 9};
//        int[] intarray = new Square().calculateV2(3);
//        assertThat(intarray, is(excepted));
//    }
}
