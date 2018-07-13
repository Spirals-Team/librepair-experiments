package ru.job4j.List;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleStackTest {

    @Test
    /**
     * Тесты для стека с типом Integer
     */
    public void getAllTestForInteger() {
        SimpleStack<Integer> simple = new SimpleStack<>();
        simple.push(1);
        simple.push(2);
        simple.push(3);
        simple.push(4);
        assertThat(simple.size(), is(4));
        simple.push(5);
        assertThat(simple.poll(), is(5));
        simple.poll();
        simple.poll();
        assertThat(simple.poll(), is(2));
     }

    @Test(expected = NoSuchElementException.class)
    /**
     * Тест ошибки poll() при окончании значений
     */
    public void getPollException() {
        SimpleStack<Integer> simple = new SimpleStack<>();
        simple.push(1);
        simple.push(2);
        simple.poll();
        simple.poll();
        simple.poll();
    }
}