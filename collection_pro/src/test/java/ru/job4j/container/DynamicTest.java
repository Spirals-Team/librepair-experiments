package ru.job4j.container;

import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;

public class DynamicTest {
    Dynamic<Integer> container = new Dynamic<>();

    @Before
    public void setUp() {
        container.add(1);
        container.add(2);
        container.add(3);
        container.add(4);
        container.add(5);
        container.add(6);
        container.add(7);
        container.add(8);
        container.add(9);
    }
    @Test
    public void whenGetPositionThatContainerIndex() {
        assertThat(container.size(), is(9));
        assertThat(container.get(0), is(1));
        assertThat(container.get(1), is(2));
        assertThat(container.get(2), is(3));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(1));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(2));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(3));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(4));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(5));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(6));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(7));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(8));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(9));
        assertThat(container.iterator().hasNext(), is(false));
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        assertThat(container.iterator().next(), is(1));
        assertThat(container.iterator().next(), is(2));
        assertThat(container.iterator().next(), is(3));
        assertThat(container.iterator().next(), is(4));
        assertThat(container.iterator().next(), is(5));
        assertThat(container.iterator().next(), is(6));
        assertThat(container.iterator().next(), is(7));
        assertThat(container.iterator().next(), is(8));
        assertThat(container.iterator().next(), is(9));
    }

    @Test public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().hasNext(), is(true));
        assertThat(container.iterator().next(), is(1));
        assertThat(container.iterator().next(), is(2));
        assertThat(container.iterator().next(), is(3));
        assertThat(container.iterator().next(), is(4));
        assertThat(container.iterator().next(), is(5));
        assertThat(container.iterator().next(), is(6));
        assertThat(container.iterator().next(), is(7));
        assertThat(container.iterator().next(), is(8));
        assertThat(container.iterator().next(), is(9));
        assertThat(container.iterator().hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shoulThrowNoSuchElementException() {
        assertThat(container.iterator().next(), is(1));
        assertThat(container.iterator().next(), is(2));
        assertThat(container.iterator().next(), is(3));
        assertThat(container.iterator().next(), is(4));
        assertThat(container.iterator().next(), is(5));
        assertThat(container.iterator().next(), is(6));
        assertThat(container.iterator().next(), is(7));
        assertThat(container.iterator().next(), is(8));
        assertThat(container.iterator().next(), is(9));
        container.iterator().next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void sholdThrowsConcurrentModificationException() {
        assertThat(container.iterator().next(), is(1));
        assertThat(container.iterator().next(), is(2));
        assertThat(container.iterator().next(), is(3));
        assertThat(container.iterator().next(), is(4));
        assertThat(container.iterator().next(), is(5));
        assertThat(container.iterator().next(), is(6));
        assertThat(container.iterator().next(), is(7));
        assertThat(container.iterator().next(), is(8));
        assertThat(container.iterator().next(), is(9));
        container.add(11);
        container.iterator().next();
    }
}