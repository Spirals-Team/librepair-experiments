package ru.aserdyuchenko.list.task_5_3_3;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author anton.
 */
public class MyStackTest {
    /**
     * Test stack.
     */
    @Test
    public void whenPopAndGetInStack() {
        MyStack<String> stack = new MyStack();
        stack.push("First");
        stack.push("Last");
        String result = stack.pop();
        assertThat(result, is("Last"));
    }

}