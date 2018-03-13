package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 *Test arithmetic sums of even numbers.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 09.10.2017
 */
public class CounterTest {

    /**
     * Test max.
     */
    @Test
    public void whenSumEvenNumbersFromOneToTenThenThirty() {
        //напишите здесь тест, проверяющий, что сумма чётных чисел от 1 до 10 при вызове метода counter.add будет равна 30.

        Counter cntr = new Counter();
        int result = cntr.add(1, 10);
        assertThat(result, is(30));

    }
}