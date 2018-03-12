package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class CalculatorTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 08.09.2017
 */
public class CalculatorTest {
    /**
     * Method whenAddOnePlusOneThenTwo. Тестируем метод add.
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
     * Method whenYouOneSubtractOneThenZero. Тестируем метод subtract.
     */
    @Test
    public void whenYouOneSubtractOneThenZero() {
        Calculator calc = new Calculator();
        calc.subtract(1D, 1D);
        double result = calc.getResult();
        double expected = 0;
        assertThat(result, is(expected));
    }

    /**
     * Method whenYouOneDivOneThenOne. Тестируем метод div.
     */
    @Test
    public void whenYouOneDivOneThenOne() {
        Calculator calc = new Calculator();
        calc.div(1D, 1D);
        double result = calc.getResult();
        double expected = 1D;
        assertThat(result, is(expected));
    }

    /**
     * Method whenYouOneMultipleOneThenOne. Тестируем метод multiple.
     */
    @Test
    public void whenYouOneMultipleOneThenOne() {
        Calculator calc = new Calculator();
        calc.multiple(1D, 1D);
        double result = calc.getResult();
        double expected = 1D;
        assertThat(result, is(expected));
    }
}
