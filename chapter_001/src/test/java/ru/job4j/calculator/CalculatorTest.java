package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * * Test.
 * *
 * * @author Artem Lipatov
 * * @version $Id$
 * * @since 0.1
 * */

public class CalculatorTest {

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenSubFiveMinusTwoThenThree() {
        Calculator calc = new Calculator();
        calc.sub(5D, 2D);
        double result = calc.getResult();
        double expected = 3D;
        assertThat(result, is(expected));
    }

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenMulSevenWithSixThenFortyTwo() {
        Calculator calc = new Calculator();
        calc.mul(7D, 6D);
        double result = calc.getResult();
        double expected = 42D;
        assertThat(result, is(expected));
    }

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenDivNineByThreeThenThree() {
        Calculator calc = new Calculator();
        calc.div(9D, 3D);
        double result = calc.getResult();
        double expected = 3D;
        assertThat(result, is(expected));
    }
}
