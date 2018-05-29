package ru.job4j.calculator;

import org.junit.Test;
import ru.job4j.Calculator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    @Test

    public void whenSubtractOneMinusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.subtract(4d, 3d);
        double result = calc.getResult();
        double expected = 1;
        assertThat(result, is(expected));
    }
    @Test

    public void whenDivOneDivOneThenTwo() {
        Calculator calc = new Calculator();
        calc.div(10D, 2D);
        double result = calc.getResult();
        double expected = 5D;
        assertThat(result, is(expected));
    }
    @Test

    public void whenMulOneMulOneThenTwo() {
        Calculator calc = new Calculator();
        calc.multiple(10D, 2D);
        double result = calc.getResult();
        double expected = 20D;
        assertThat(result, is(expected));
    }
}