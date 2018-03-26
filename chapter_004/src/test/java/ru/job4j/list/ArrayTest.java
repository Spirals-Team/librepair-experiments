package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class ArrayTest {
    @Test
    public void whenAddGoesBeyondArrayBoundsArrayIsExpanding() {
        Array<Integer> array = new Array<>();

        for (int i = 0; i < 11; i++) {
            array.add(i + 1);
        }

        Integer[] expected = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertThat(array.toArray(), is(expected));
    }

    @Test
    public void whenGetElementReturnsElement() {
        Array<Integer> array = new Array<>();

        for (int i = 0; i < 11; i++) {
            array.add(i);
        }

        assertThat(array.get(7), is(7));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIterateReturnsExpectedElement() {
        Array<Integer> array = new Array<>();

        array.add(1);
        array.add(2);

        Iterator<Integer> it = array.iterator();
        while (it.hasNext()) {
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(1));
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(2));
            assertThat(it.hasNext(), is(false));
            it.next();
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddWhileIteratingThrowsException() {
        Array<Integer> array = new Array<>();

        array.add(1);
        array.add(2);

        Iterator<Integer> it = array.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            array.add(3);
        }
    }
}
