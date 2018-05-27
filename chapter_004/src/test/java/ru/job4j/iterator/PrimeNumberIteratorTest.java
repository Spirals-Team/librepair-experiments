package ru.job4j.iterator;

import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class PrimeNumberIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void whenNotEvenNumberInArray() {
        Iterator<Integer> iterator = new PrimeNumberIterator(new int[] {1821, 3123, 735});
        iterator.next();
    }

    @Test
    public void whenTestCorrectIterator() {
        Iterator<Integer> iterator = new PrimeNumberIterator(new int[] {2441, 240, 241, 1053});
        assertThat(iterator.next(), is(2441));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(241));
        assertThat(iterator.hasNext(), is(false));
    }
}