package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class CounterTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.09.2017
 */
public class CounterTest {
    /**
     * Method whenSumEvenNumbersFromOneToTenThenThirty. Тестируем метод add.
     */
    @Test
    public void whenSumEvenNumbersFromOneToTenThenThirty() {
        Counter counter = new Counter();
        int resault = counter.add(1, 10);
        assertThat(resault, is(30));
    }
}
