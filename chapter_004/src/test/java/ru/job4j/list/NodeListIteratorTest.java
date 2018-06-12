package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class NodeListIteratorTest {

    private NodeList<String> list;
    private Iterator<String> it;

    @Before
    public void setUp() {
        list = new NodeList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        it = list.iterator();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnNextElement() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("one"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("two"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("three"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("four"));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("one"));
        assertThat(it.next(), is("two"));
        assertThat(it.next(), is("three"));
        assertThat(it.next(), is("four"));
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
        assertThat(list.get(0), is("one"));
        assertThat(list.get(1), is("two"));
        assertThat(list.get(2), is("three"));
        assertThat(list.get(3), is("four"));
        list.get(4);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenGetElemWithNegativeIndexThenException() {
        list.get(-1);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddFirstThenElementsAddInTheStartOfList() {
        NodeList<String> list = new NodeList<>();
        list.addFirst("three");
        list.addFirst("two");
        it = list.iterator();
        list.addFirst("one");
        assertThat(list.get(0), is("one"));
        assertThat(list.get(1), is("two"));
        assertThat(list.get(2), is("three"));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenDeleteFirstThenElementsDeleteFromTheStartOfList() {
        NodeList<String> list = new NodeList<>();
        list.addFirst("three");
        list.addFirst("two");
        list.addFirst("one");
        it = list.iterator();
        list.deleteFirst();
        assertThat(list.get(0), is("two"));
        assertThat(list.get(1), is("three"));
        it.hasNext();
    }
}
