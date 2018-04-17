package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.iterator.PrimeIterator;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class DynamicArrayTest {

    private DynamicArray<String> list;
    private Iterator<String> it;

    @Before
    public void setUp() {
        list = new DynamicArray<>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        it = list.iterator();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnNextElement() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("first"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("second"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("third"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("fourth"));
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
        assertThat(it.next(), is("fourth"));
    }

    @Test
    public void shouldReturnFalseCauseThereIsNoAnyElements() {
        it = new DynamicArray<String>().iterator();
        assertThat("should return false, cause there is no any prime number", it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddElementThenDynamicArrayHasNewElement() {
        list.add("newElement");
        assertThat(list.get(4), is("newElement"));
        it.hasNext();
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenGetElementThenRetuenWithTheIndex() {
        assertThat(list.get(0), is("first"));
        assertThat(list.get(1), is("second"));
        assertThat(list.get(2), is("third"));
        assertThat(list.get(3), is("fourth"));
        list.get(4);
    }
}