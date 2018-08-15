package ru.job4j.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Class SimpleArrayTest.
 */
public class SimpleArrayTest {
    /**
     * Test methods add and get.
     */
    @Test
    public void whenCreateContainerShouldReturnTheSameType() {
        SimpleArray<Integer> simple = new SimpleArray<>(4);

        simple.add(5);
        simple.add(3);
        Integer result = simple.get(1);

        assertThat(result, is(3));
    }

    /**
     * Test method update.
     */
    @Test
    public void whenUpdateOldObjectThenOldObjectReplacNewObject() {
        SimpleArray<String> simple = new SimpleArray<>(3);
        String s = "new boy";

        simple.add("Girl");
        simple.add("Boy");
        simple.update(1, s);
        String result = simple.get(1);

        assertThat(result, is(s));
    }

    /**
     * Test method delete.
     */
    @Test
    public void whenDeleteObjectWhenNextObjectReplaceDeleteObject() {
        SimpleArray<Character> simple = new SimpleArray<>(3);

        simple.add('A');
        simple.add('B');
        simple.add('C');
        simple.delete('A');
        Character result = simple.get(0);

        assertThat(result, is('B'));
    }
}