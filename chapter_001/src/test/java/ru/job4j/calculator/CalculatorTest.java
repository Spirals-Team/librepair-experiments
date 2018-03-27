package ru.job4j.calculator;


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
public class CalculatorTest {
    /**
     * Test add.
     */
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    /**
     * Test subtract.
     */
    @Test
    public void whenSubtractTwoSubtractOneThenOne() {
        Calculator calc = new Calculator();
        calc.subtract(2D, 1D);
        double rezult = calc.getResult();
        double ex = 1D;
        assertThat(rezult, is(ex));
    }
    /**
     * Test div.
     */
    @Test
    public void whenDivFourDivTwoThenTwo() {
        Calculator calc = new Calculator();
        calc.div(4d, 2d);
        double rezult = calc.getResult();
        double ex = 2d;
        assertThat(rezult, is(ex));
    }
    /**
     * Test multiple.
     */
    @Test
    public void whenMultipleTwoMultipleTwoThenFour() {
        Calculator calc = new Calculator();
        calc.multiple(2d, 2d);
        double rezult = calc.getResult();
        double ex = 4d;
        assertThat(rezult, is(ex));
    }
}
