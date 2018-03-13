package ru.job4j.generic;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {

    @Test
    public void whenCreateContainerShouldReturnTheSameType() {
        SimpleArray<String> simple = new SimpleArray<String>(4);

        simple.add("test");
        String result = (String) simple.get(0);

        assertThat(result, is("test"));
    }

    @Test
    public void whenTypeIntShouldReturnInt() {

        SimpleList simple = new Stack(4);
        simple.add(2);
        int result = (int) simple.get(0);

        assertThat(result, is(2));
    }

    @Test
    public void whenUpdateThenGetUpdatedValue() {

        SimpleArray<String> simple = new SimpleArray<>(4);

        simple.add("test");
        simple.update(0, "test2");
        String result =  simple.get(0);

        assertThat(result, is("test2"));

    }

    @Test
    public void whenDeleteThenTheElementDeleted() {

        SimpleArray<String> simple = new SimpleArray<>(4);

        String valueTest1 = new String("test1");
        String valueTest2 = new String("test2");

        simple.add(valueTest1);
        simple.add(valueTest2);

        simple.delete(0);

        String result = (String) simple.get(0);

        assertThat(result, is("test2"));

    }

}