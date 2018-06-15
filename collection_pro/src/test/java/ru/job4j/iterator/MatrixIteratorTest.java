package ru.job4j.iterator;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MatrixIteratorTest {
    private Iterator<Integer> it;

    @Before
    public void setUp() {
        it = new MatrixIterator(new int[][]{{1, 2, 3}, {4, 5, 6}});
    }

    @Test
    public void whenGetNextCallSholdPointerForward() {
        MatrixIterator it = new MatrixIterator(new int[][] {
                {1, 2},
                {3, 4, 5}
    });
        it.next();
        it.next();
        it.next();
        it.next();
        int result = (Integer) it.next();
        assertThat(result, is(5));
    }

    @Test
    public void whenCheckNextPositionShouldReturnConantValue() {
        MatrixIterator it = new MatrixIterator(new int[][]{
                {1, 2},
                {3, 4}
        });
        it.next();
        it.hasNext();
        it.hasNext();

        boolean result = it.hasNext();

        assertThat(result, is(false));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(1));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(2));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(3));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(4));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(5));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(6));
        assertThat(it.hasNext(), Matchers.is(false));
    }
    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        assertThat(it.next(), Matchers.is(1));
        assertThat(it.next(), Matchers.is(2));
        assertThat(it.next(), Matchers.is(3));
        assertThat(it.next(), Matchers.is(4));
        assertThat(it.next(), Matchers.is(5));
        assertThat(it.next(), Matchers.is(6));
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.hasNext(), Matchers.is(true));
        assertThat(it.next(), Matchers.is(1));
        assertThat(it.next(), Matchers.is(2));
        assertThat(it.next(), Matchers.is(3));
        assertThat(it.next(), Matchers.is(4));
        assertThat(it.next(), Matchers.is(5));
        assertThat(it.next(), Matchers.is(6));
    }

    @Test(expected = NoSuchElementException.class)
    public void shoulThrowNoSuchElementException() {
        it = new MatrixIterator(new int[][]{});
        it.next();
    }


}