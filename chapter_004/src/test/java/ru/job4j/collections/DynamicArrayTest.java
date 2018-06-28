package ru.job4j.collections;

import org.junit.Test;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DynamicArrayTest {

    DynamicArray<Object> array = new DynamicArray<Object>();

    @Test(expected = NoSuchElementException.class)
        public void whetIterHasNotNextThenThrowException() {

            array.add(7);
            array.add("1");
            array.add(new Integer(4));
            array.add(4.5);
            array.add(true);

            Iterator<Object> iter = array.iterator();
            assertThat(iter.hasNext(), is(true));
            assertThat(iter.next(), is(7));
            assertThat(iter.hasNext(), is(true));
            assertThat(iter.next(), is("1"));
            assertThat(iter.hasNext(), is(true));
            assertThat(iter.next(), is(4));
            assertThat(iter.hasNext(), is(true));
            assertThat(iter.next(), is(4.5));
            assertThat(iter.hasNext(), is(true));
            assertThat(iter.next(), is(true));
            assertThat(iter.hasNext(), is(false));
            iter.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenUpdateIterThenThrowException() {

        array.add(7);
        Iterator<Object> iter = array.iterator();
        iter.hasNext();
        array.add("new");
        iter.hasNext();
    }
}