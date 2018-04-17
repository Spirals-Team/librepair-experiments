package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Yury Matskevich (y.n.matskevich@gmail.com)
 * @version $Id$
 */

public class CalculatorTest {
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResultAdd();
        double expected = 2D;
        assertThat(result, is(expected));
    }

    @Test
    public void whenSubThreeMinusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.sub(3, 1);
        double result = calc.getResultSub();
        double expected = 2;
        assertThat(result, is(expected));
    }

    @Test
    public void whenDivSixDivisionThreeThenTwo() {
        Calculator calc = new Calculator();
        calc.div(6, 3);
        double result = calc.getResultDiv();
        double expected = 2;
        assertThat(result, is(expected));
    }

    @Test
    public void whenMulThreeMultiplicationTwoThenSix() {
        Calculator calc = new Calculator();
        calc.mul(3, 2);
        double result = calc.getResultMul();
        double expected = 6;
        assertThat(result, is(expected));
    }
}