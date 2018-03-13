package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleLinkedSetTest {

    @Test
    public void showSimpleLinkedSet() {

        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("first");
        set.add("second");
        set.add("zero");
        set.add("zero");
        set.add("third");
        for (String value : set) {
            System.out.println(value);
        }
    }

    @Test
    public void whenAddToSimpleLinkedSetDuplicatedValueThenInSimpleLinkedSetThereAreNoDuplicatedValues() {

        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("first");
        set.add("second");
        set.add("zero");

        set.add("third");

        int expected = set.getSize();

        set.add("zero");

        int result = set.getSize();

        assertThat(result, is(expected));

    }
}
