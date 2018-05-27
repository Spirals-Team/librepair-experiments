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
public class TwoDimensionalArrayIteratorTest {

    @Test(expected = NoSuchElementException.class)
    public void whenNotElementInMatrix() {
        Iterator<Integer> it = new TwoDimensionalArrayIterator(new int[][]{});
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void whenTestSquareMatrix() {
        Iterator<Integer> it = new TwoDimensionalArrayIterator(new int[][]{{1, 2}, {3, 4}});
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void whenTestNoSquareMatrix() {
        Iterator<Integer> it = new TwoDimensionalArrayIterator(
                new int[][]{{1}, {2, 3, 4, 5}, {6, 7}, {8, 9, 10, 11, 12, 13, 14}}
                );
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(7));
        assertThat(it.next(), is(8));
        assertThat(it.next(), is(9));
        assertThat(it.next(), is(10));
        assertThat(it.next(), is(11));
        assertThat(it.next(), is(12));
        assertThat(it.next(), is(13));
        assertThat(it.next(), is(14));
        assertThat(it.hasNext(), is(false));
    }
}