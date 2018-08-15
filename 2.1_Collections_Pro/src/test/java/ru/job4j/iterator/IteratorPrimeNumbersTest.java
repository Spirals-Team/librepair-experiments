package ru.job4j.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test IteratorPrimeNumbersTest.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class IteratorPrimeNumbersTest {
    /**
     * Array have two prime values.
     */
    @Test
    public void whenArrayHaveTwoPrimeValuesThenSizeOfListIs2() {
        List<Integer> result = new ArrayList<>();
        Iterator iterator = new IteratorPrimeNumbers(new int[] {3, 4, 1, 7});
        while (iterator.hasNext()) {
            result.add((Integer) iterator.next());
        }
        List<Integer> expect = Arrays.asList(3, 7);
        assertThat(result, is(expect));
    }

    /**
     * Array have three prime values.
     */
    @Test
    public void whenArrayHaveThreePrimeValuesThenSizeOfListIs3() {
        List<Integer> result = new ArrayList<>();
        Iterator iterator = new IteratorPrimeNumbers(new int[] {1, 2, 3, 4, 11, 10, 12, 15, 19});
        while (iterator.hasNext()) {
            result.add((Integer) iterator.next());
        }
        List<Integer> expect = Arrays.asList(2, 3, 11, 19);
        assertThat(result, is(expect));
    }
}