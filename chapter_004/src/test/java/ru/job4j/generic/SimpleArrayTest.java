package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleArrayTest {

    private SimpleArray<String> strings;
    private Iterator<String> it;

    @Before
    public void setUp() {
        strings = new SimpleArray<>(6);
        strings.add("first");
        strings.add("second");
        strings.add("third");
        it = strings.iterator();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnPrimeNumbersOnly() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("first"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("second"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("third"));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("first"));
        assertThat(it.next(), is("second"));
        assertThat(it.next(), is("third"));
    }

    @Test
    public void shouldReturnFalseCauseThereIsNoAnyPrimeNumber() {
        it = new SimpleArray<String>(6).iterator();
        assertThat("should return false, cause there is no any string", it.hasNext(), is(false));
    }


    @Test
    public void whenAddThenSimpleArrayHasNewElement() {
        SimpleArray<String> strings = new SimpleArray<>(10);
        Iterator<String> iterator = strings.iterator();
        strings.add("first");
        assertThat(iterator.next(), is("first"));
    }

    @Test
    public void whenUseGetThenGetElemByIndex() {
        assertThat(strings.get(0), is("first"));
        assertThat(strings.get(1), is("second"));
        assertThat(strings.get(2), is("third"));
    }

    @Test
    public void whenSetThenPutElemInThePosition() {
        strings.set(0, "newString1");
        strings.set(2, "newString2");
        assertThat(strings.get(0), is("newString1"));
        assertThat(strings.get(2), is("newString2"));
    }

    @Test
    public void whenDeleteThenSimpleArrayDoesNotHaveTheElement() {
        strings.delete(1);
        assertThat(strings.get(1), is("third"));
        assertNull(strings.get(2));
        strings.delete(0);
        assertThat(strings.get(0), is("third"));
    }
}