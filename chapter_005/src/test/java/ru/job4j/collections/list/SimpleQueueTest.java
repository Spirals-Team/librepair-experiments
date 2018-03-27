package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests Queue.
 * @author Hincu Andrei (andreih1981@gmail.com) by 10.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleQueueTest {
    /**
     * Хранилище тип Очередь.
     */
    private SimpleQueue<String> strings;

    /**
     * Инициализация.
     */
    @Before
    public void init() {
        strings = new SimpleQueue<>();
        strings.push("Ivanov");
        strings.push("Petrov");
    }

    /**
     *Тест метода poll.
     */
    @Test
    public void whenWasCalledTwoElementsThenQueueIsEmpty() {
        String ivanov = strings.poll();
        assertThat(ivanov, is("Ivanov"));
        String petrov = strings.poll();
        assertThat(petrov, is("Petrov"));
        assertThat(strings.getSize(), is(0));
    }

    /**
     * Тест метода push.
     */
    @Test
    public void whenWasAddedTwoElements() {
        assertThat(strings.getSize(), is(2));
    }

}