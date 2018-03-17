package ru.job4j.collections.list;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Test Stac metods.
 * @author Hincu Andrei (andreih1981@gmail.com) by 10.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleStacTest {
    /**
     * Хранилище стэк.
     */
    private SimpleStac<String> stac;

    /**
     * Инициализация.
     */
    @Before
    public void init() {
        stac = new SimpleStac<>();
        stac.push("Ivanov");
        stac.push("Sidorov");
    }

    /**
     * Тест метода poll().
     */
    @Test
    public void whenWasCalledPollThenStacIsEmpty() {
        assertThat(stac.getSize(), is(2));
        String sidorov = stac.poll();
        assertThat(stac.getSize(), is(1));
        assertThat(sidorov, is("Sidorov"));
        String ivanov = stac.poll();
        assertThat(ivanov, is("Ivanov"));
        assertThat(stac.getSize(), is(0));
    }

    /**
     * Тест метода push.
     */
    @Test
    public void whenWasPushTwoNewElements() {
        assertThat(stac.getSize(), is(2));
    }

}