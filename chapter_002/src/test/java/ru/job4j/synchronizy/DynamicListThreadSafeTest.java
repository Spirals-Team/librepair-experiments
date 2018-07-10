package ru.job4j.synchronizy;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DynamicListThreadSafeTest {


    private DynamicListThreadSafe<Integer> ints;

    @Test
    public void givenFullContainerWhenAddAnotherItemThenContainerIsIncreasedTwice() {
        ints = new DynamicListThreadSafe<>();
        ints.add(3);
        assertThat(ints.get(0), is(3));
        ints.add(5);
        assertThat(ints.get(1), is(5));
        assertThat(ints.length(), is(2));
        ints.add(7);
        assertThat(ints.get(2), is(7));
        assertThat(ints.length(), is(4));
    }

    @Test
    public void givenFullVarArgsConstructorWhenAddAnotherItemThenContainerIsIncreasedTwice() {
        ints = new DynamicListThreadSafe<>(5, 6, 3);
        assertThat(ints.get(0), is(5));
        assertThat(ints.get(1), is(6));
        assertThat(ints.get(2), is(3));
        ints.add(9);
        assertThat(ints.get(3), is(9));
        assertThat(ints.length(), is(6));
    }

    @Test
    public void givenConstructorContainerInitializedBySizeWhenAddAnotherItemThenContainerIsIncreasedTwice() {
        ints = new DynamicListThreadSafe<>(2);
        ints.add(3);
        assertThat(ints.get(0), is(3));
        ints.add(5);
        assertThat(ints.get(1), is(5));
        ints.add(7);
        assertThat(ints.get(2), is(7));
        assertThat(ints.length(), is(4));
    }

    @Test(expected = NoSuchElementException.class)
    public void givenIteratorWhenGoesOverBorderThenThrowException() {
        ints = new DynamicListThreadSafe<>(5, 6, 3);
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
        ints = new DynamicListThreadSafe<>(5, 6, 3);
        Iterator it = ints.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        ints.add(10);
        it.next();
    }

    @Test
    public void severalThreadsWorkWithCollection() throws InterruptedException {
        ints = new DynamicListThreadSafe<>();

        for (int i = 0; i < 200; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        ints.add(10);
                    }
                }
            };
            t.start();
            t.join();
        }
        int sum = 0;
        for (Integer anInt : ints) {
            sum += anInt;
        }
        assertThat(sum, is(2000000));
    }
}