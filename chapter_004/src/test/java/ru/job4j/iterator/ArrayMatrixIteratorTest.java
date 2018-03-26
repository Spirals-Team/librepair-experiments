package ru.job4j.iterator;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArrayMatrixIteratorTest {
    @Test
    public void whenIterateThreeTimesReturnsFour() {
        ArrayMatrixIterator it = new ArrayMatrixIterator(new int[][] {{1, 3}, {4, 7, 5}});

        it.next();
        it.next();
        int result = it.next();

        assertThat(result, is(4));
    }

    @Test
    public void whenIterateFiveTimesHasNextIsFalse() {
        ArrayMatrixIterator it = new ArrayMatrixIterator(new int[][] {{1, 3}, {4, 7, 5}});

        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }

    @Test
    public void whenIterateFourTimesHasNextIsTrue() {
        ArrayMatrixIterator it = new ArrayMatrixIterator(new int[][] {{1, 3}, {4, 7, 5}});

        it.next();
        it.next();
        it.next();
        it.next();
        boolean result = it.hasNext();

        assertThat(result, is(true));
    }

    private Iterator<Integer> it;

    @Before
    public void setUp(){
        it = new ArrayMatrixIterator(new int[][]{{1}, {3, 4}, {7}});
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation () {
        assertThat(it.next(), Matchers.is(1));
        assertThat(it.next(), Matchers.is(3));
        assertThat(it.next(), Matchers.is(4));
        assertThat(it.next(), Matchers.is(7));
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder () {
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(1));
        assertThat(it.next(), Matchers.is(3));
        assertThat(it.next(), Matchers.is(4));
        assertThat(it.next(), Matchers.is(7));
    }

    @Test(expected = NoSuchElementException.class)
    public void hasNextNextSequentialInvocation () {
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(1));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(3));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(4));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(7));
        assertThat(it.hasNext(), Matchers.is(false));
        it.next();
    }
}