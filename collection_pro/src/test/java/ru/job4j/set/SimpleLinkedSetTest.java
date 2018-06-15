package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleLinkedSetTest {
    SimpleLinkedSet<String> set;

    @Before
    public void setUp() {

        set = new SimpleLinkedSet();
        set.add("one");
        set.add("one");
        set.add("two");
        set.add("two");
        set.add("third");
       // set.add("two");
    }

    @Test
    public void whenSimpleSetAddWithoutDuplicate() {

        assertThat(set.size(), is(3));

    }
    @Test
    public void hasNextNextSequentialInvocation() {
        assertThat(set.iterator().hasNext(), is(true));
        assertThat(set.iterator().next(), is("one"));
        assertThat(set.iterator().hasNext(), is(true));
        assertThat(set.iterator().next(), is("two"));
        assertThat(set.iterator().hasNext(), is(true));
        assertThat(set.iterator().next(), is("third"));
//        assertThat(set.iterator().hasNext(), is(true));
        //      assertThat(set.iterator().next(), is(4));
        assertThat(set.iterator().hasNext(), is(false));
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        assertThat(set.iterator().next(), is("one"));
        assertThat(set.iterator().next(), is("two"));
        assertThat(set.iterator().next(), is("third"));
    }

    @Test public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(set.iterator().hasNext(), is(true));
        assertThat(set.iterator().hasNext(), is(true));
        assertThat(set.iterator().next(), is("one"));
        assertThat(set.iterator().next(), is("two"));
        assertThat(set.iterator().next(), is("third"));
        assertThat(set.iterator().hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shoulThrowNoSuchElementException() {
        assertThat(set.iterator().next(), is("one"));
        assertThat(set.iterator().next(), is("two"));
        assertThat(set.iterator().next(), is("third"));
        set.iterator().next();
    }




}