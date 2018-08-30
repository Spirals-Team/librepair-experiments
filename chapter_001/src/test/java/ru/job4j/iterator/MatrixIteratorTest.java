package ru.job4j.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class MatrixIteratorTest {

    private Iterator it;

    @Test(expected = NoSuchElementException.class)
    public void squareArrayHasNextNextSequentialInvocation() {
        it = new MatrixIterator(new int[][]{{1, 2, 3}, {4, 5, 6}});
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(6));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void squareArrayHasNextInvocationNextMethodDoesntDependsOnPrior() {
        it = new MatrixIterator(new int[][]{{1, 2, 3}, {4, 5, 6}});
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void squareArrayHasNextSequentialInvocationDoesntAffectRetrievalOrder() {
        it = new MatrixIterator(new int[][]{{1, 2, 3}, {4, 5, 6}});
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void jaggedArrayHasNextInvocationNextMethodDoesntDependsOnPrior() {
        it = new MatrixIterator(new int[][]{{1}, {3, 4}});
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void jaggedArrayHasNextSequentialInvocationDoesntAffectRetrievalOrder() {
        it = new MatrixIterator(new int[][]{{1}, {3, 4}});
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void jaggedArrayHasNextNextSequentialInvocation() {
        it = new MatrixIterator(new int[][]{{1}, {3, 4}});
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}
