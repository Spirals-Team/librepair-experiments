package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test counting factorial.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 09.10.2017
 */
public class FactorialTest {

    /**
     * Test factorial.
     */
    @Test
    public void whenCalculateFactorialForFiveThenOneHundreedTwenty() {
        //напишите здесь тест, проверяющий, что факториал для числа 5 равен 120.
        Factorial factor = new Factorial();
        int result = factor.factorial(5);
        assertThat(result, is(120));
    }

    /**
     * Test factorial.
     */
    @Test
    public void whenCalculateFactorialForZeroThenOne() {
        //напишите здесь тест, проверяющий, что факториал для числа 0 равен 1.
        Factorial factor = new Factorial();
        int result = factor.factorial(0);
        assertThat(result, is(1));
    }
}