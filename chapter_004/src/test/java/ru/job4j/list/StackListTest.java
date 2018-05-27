package ru.job4j.list;

import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class StackListTest {
    @Test(expected = NoSuchElementException.class)
    public void whenTestStackPushAndPullElements() {
        StackList<Integer> stack = new StackList<>();
        stack.push(100);
        stack.push(77);
        stack.push(999);
        assertThat(stack.pull(), is(999));
        assertThat(stack.pull(), is(77));
        assertThat(stack.pull(), is(100));
        stack.pull();
    }
}