package ru.job4j.calculator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    @Test
    public void whenSubFiveAndOneThenFour() {
        Calculator calc = new Calculator();
        calc.sub(5D, 1D);
        double result = calc.getResult();
        double expected = 4D;
       assertThat(result, is(expected));
    }
    @Test
    public void whenDivSixAndTwoThenThree() {
        Calculator calc = new Calculator();
        calc.div(6D, 2D);
        double result = calc.getResult();
        double expected = 3D;
        assertThat(result, is(expected));
    }
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    @Test
    public void whenMultTwoAndTwoThenFour() {
        Calculator calc = new Calculator();
        calc.mult(2D, 2D);
        double result = calc.getResult();
        double expected = 4D;
        assertThat(result, is(expected));
    }
}