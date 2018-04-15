package ru.aserdyuchenko.generic.task_5_2_1;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class SimpleArrayTest {
    /**
     * Add string.
     */
    @Test
    public void whenCreateContainerShouldReturnTheSameType() {
        SimpleArray<String> simpleArray = new SimpleArray<String>(4);
        simpleArray.add("test");
        String result = simpleArray.get(0);
        assertThat(result, is("test"));
    }

    /**
     * Add int.
     */
    @Test
    public void whenTypeIntShouldReturnInt() {
        SimpleArray<Integer> simpleArray = new SimpleArray<Integer>(4);
        simpleArray.add(2);
        int result = simpleArray.get(0);
        assertThat(result, is(2));
    }

    /**
     * Update.
     */
    @Test
    public void whenUpdateNumber() {
        SimpleArray<Integer> simpleArray = new SimpleArray<Integer>(4);

        simpleArray.add(2);
        simpleArray.update(0, 7);
        int result = simpleArray.get(0);

        assertThat(result, is(7));
    }

    /**
     * Delete.
     */
    @Test
    public void whenDeleteNumber() {
        SimpleArray<Integer> simpleArray = new SimpleArray<Integer>(4);

        simpleArray.add(1);
        simpleArray.add(2);
        simpleArray.add(3);
        simpleArray.delete(1);
        int result = simpleArray.get(1);

        assertThat(result, is(3));
    }
}