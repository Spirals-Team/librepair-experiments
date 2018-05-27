package ru.job4j.iterator;

import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class EvenNumberIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void whenNotEvenNumberInArray() {
        Iterator<Integer> iterator = new EvenNumberIterator(new int[] {1, 3, 5});
        iterator.next();
    }

    @Test
    public void whenTestCorrectIterator() {
        Iterator<Integer> iterator = new EvenNumberIterator(new int[] {4, 2, 1, 1});
        assertThat(iterator.next(), is(4));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(false));
    }
}