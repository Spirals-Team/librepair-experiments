package ru.job4j.bombermen;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Класс для тестирования игры
 */
public class BombermenTest {

    @Test
    public void testBombermen() {
        Bombermen b = new Bombermen(5, 5, 8);
        Thread thread = new Thread(b);
        b.init();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(thread.getState(), is(Thread.State.TERMINATED));
    }
}