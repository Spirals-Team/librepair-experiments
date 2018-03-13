package ru.job4j.iterator;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class IteratorArrayTest {

    public static final class ForEachArray implements Iterable {

        private final int[] values;

        public ForEachArray(final int[] values) {
            this.values = values;
        }

        @Override
        public Iterator iterator() {
            return new IteratorArray(this.values);
        }
    }

    @Test
    public void whenGetNextCallShouldPointerForward() {
        IteratorArray it = new IteratorArray(new int[] {1, 3});

        it.next();
        int result = (Integer) it.next();

        assertThat(result, is(3));
    }

    @Test
    public void whenCheckNextPositionShouldReturnContantValue() {
        IteratorArray it = new IteratorArray(new int[] {1});

        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }

    @Test
    public void foreach() {
        ForEachArray foreach = new ForEachArray(new int[] {1, 5, 3});

        for (Object value : foreach) {
            System.out.println(value);
        }
    }

    @Test
    public void foreachArray() {
        int[] foreach = new int[] {1, 5, 3};

        for (int value : foreach) {
            System.out.println(value);
        }
    }

}
