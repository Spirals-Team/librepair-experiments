package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CounterTest {

    /**
     * Тест проверяет, что сумма чётных чисел от 1 до 10 при вызове метода counter.add будет равна 30.
     */
    @Test
    public void whenSumEvenNumbersFromOneToTenThenThirty() {
        Counter counter = new Counter();
        int result = counter.add(1, 10);
        assertThat(result, is(30));
    }

    /**
     * Тест проверяет, что сумма чётных чисел от 2 до 1 при вызове метода counter.add будет равна -1.
     * Т.к. ввден некоректный диапазон значений.
     */
    @Test
    public void testErrorNumber() {
        Counter counter = new Counter();
        int result = counter.add(2, 1);
        assertThat(result, is(-1));
    }
}