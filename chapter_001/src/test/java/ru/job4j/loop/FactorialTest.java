package ru.job4j.loop;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class FactorialTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.09.2017
 */
public class FactorialTest {
    /**
     * Method whenCalculateFactorialForFiveThenOneHundreedTwenty. Тестируем метод calc.
     */
    @Test
    public void whenCalculateFactorialForFiveThenOneHundreedTwenty() {
        Factorial factorial = new Factorial();
        int resault = factorial.calc(5);
        assertThat(resault, is(120));
    }

    /**
     * Method whenCalculateFactorialForZeroThenOne. Тестируем метод calc.
     */
    @Test
    public void whenCalculateFactorialForZeroThenOne() {
        Factorial factorial = new Factorial();
        int resault = factorial.calc(0);
        assertThat(resault, is(1));
    }
}
