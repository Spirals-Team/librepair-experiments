package ru.job4j.set;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SimpleSetTest {

    SimpleSet<Integer> ints;

    @Test

    public void givenSetWhenAddDuplicateThenContainerNotContainDuplicate() {
        ints = new SimpleSet<>();
        ints.add(1);
        assertThat(ints.toString(), is("1"));
        ints.add(1);
        assertThat(ints.toString(), is("1"));
        ints.add(2);
        assertThat(ints.toString(), is("12"));
        ints.add(3);
        assertThat(ints.toString(), is("123"));
        ints.add(1);
        assertThat(ints.toString(), is("123"));
    }

    @Test
    public void givenFullVarArgsConstructorWhenPassDuplicateThenContainerNotContainDuplicate() {
        ints = new SimpleSet<>(5, 6, 3, 5, 3);
        assertThat(ints.toString(), is("563"));
    }

    @Test(expected = NoSuchElementException.class)
    public void givenIteratorWhenGoesOverBorderThenThrowException() {
        ints = new SimpleSet<>(5, 6, 3);
        Iterator it = ints.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void givenIteratorWhenModifyListThenThrowException() {
        ints = new SimpleSet<>(5, 6, 3);
        Iterator it = ints.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        ints.add(10);
        it.next();
    }
}