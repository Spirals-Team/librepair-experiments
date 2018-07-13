package ru.job4j.generic;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {

    @Test
    /**
     * Тесты для контейнера с типом String
     */
    public void getAllTestForString() {
        SimpleArray<String> simple = new SimpleArray<>(5);
        Iterator<String> simpleiter = simple.iterator();
        simple.add("first");
        simple.add("second");
        simple.add("third");
        simple.add("four");
        assertThat(simple.size(), is(4));
        simple.add("five");
        assertThat(simple.get(1), is("second"));
        simple.set(3,"four");
        assertThat(simple.get(3), is("four"));
        simple.add("six");
        assertThat(simple.get(5), is("six"));
        simple.delete(1);
        assertThat(simple.get(1), is("third"));
        assertThat(simpleiter.next(),is("first"));
        assertThat(simpleiter.hasNext(), is(true));
    }

    @Test
    /**
     * Тесты для контейнера с типом Integer
     */
    public void getAllTestForInteger() {
        SimpleArray<Integer> simple = new SimpleArray<>(5);
        Iterator<Integer> simpleiter = simple.iterator();
        simple.add(1);
        simple.add(2);
        simple.add(3);
        simple.add(4);
        assertThat(simple.size(), is(4));
        simple.add(5);
        assertThat(simple.get(1), is(2));
        simple.set(3,4);
        assertThat(simple.get(3), is(4));
        simple.add(6);
        assertThat(simple.get(5), is(6));
        simple.delete(1);
        assertThat(simple.get(1), is(3));
        assertThat(simpleiter.next(),is(1));
        assertThat(simpleiter.hasNext(), is(true));
    }

}