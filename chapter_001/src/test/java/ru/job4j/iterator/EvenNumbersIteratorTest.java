package ru.job4j.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EvenNumbersIteratorTest {

    private Iterator it;

    @Test(expected = NoSuchElementException.class)
    public void emptyArray() {
        it = new EvenNumbersIterator(new int[]{});
        assertThat(it.hasNext(), is(false));
        assertThat(it.hasNext(), is(false));
        it.next();
    }


    @Test(expected = NoSuchElementException.class)
    public void arrayWithoutEvenNumbers() {
        it = new EvenNumbersIterator(new int[]{1, 3, 5, 7});
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnEvenNumbersSequentially() {
        it = new EvenNumbersIterator(new int[]{1, 2, 3, 4, 5, 6, 7});
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(6));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        it = new EvenNumbersIterator(new int[]{1, 2, 3, 4, 5, 6, 7});
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(6));
    }

    @Test
    public void shouldReturnFalseIfNoAnyEvenNumbers() {
        it = new EvenNumbersIterator(new int[]{1});
        assertThat(it.hasNext(), is(false));
    }
}