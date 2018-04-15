package ru.aserdyuchenko.iterator.task5_1_3;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author anton.
 */
public class IteratorFotPrimeNumberTest {
    /**
     * Test method hasNext().
     */
    @Test
    public void whenCheckNextPrimeNumber() {
        IteratorFotPrimeNumber it = new IteratorFotPrimeNumber(new int[] {4, 5, 6, 7, 8});
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }

    /**
     * Test method next().
     */
    @Test
    public void whenGetNextPrimeNumber() {
        IteratorFotPrimeNumber it = new IteratorFotPrimeNumber(new int[] {4, 5, 6, 7, 8});
        it.next();
        int result = it.next();
        assertThat(result, is(7));
    }
}