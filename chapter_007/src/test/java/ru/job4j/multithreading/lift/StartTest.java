package ru.job4j.multithreading.lift;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 12.01.18;
 * @version $Id$
 * @since 0.1
 */
public class StartTest {
    private Lift lift;
    private Input input;
    private ArrayBlockingQueue<Integer> in;
    private ArrayBlockingQueue<Integer> ex;

    @Before
    public void init() {
        this.in = new ArrayBlockingQueue<Integer>(20);
        this.ex = new ArrayBlockingQueue<Integer>(20);
        this.lift = new Lift("20", "5", "4", "3", ex, in);

    }

    @Test
    public void whenFirstLiftWasCallAt3FloorAndThenAt5Floor() throws Exception {
        String[]ans = {"3", "L", "5", "0"};
        this.input = new ImputForTest(in, ex, "20", ans);
        Thread t = new Thread(lift);
        t.start();
        Thread t2 = new Thread(this.input);
        t2.start();
        t2.join();
        t.join();
        assertThat(lift.getCurrentPosition(), is(4));
    }

    /**
     * Проверяемое значение меньше на 1 т.к нумерация в лифте идет с 1
     * а индексы в массиве с 0
     * @throws Exception ex.
     */
    @Test
    public void whenFirtLiftWasCallAt3Then5Then1() throws Exception {
        String[]ans = {"3", "L", "5", "p", "1", "0"};
        this.input = new ImputForTest(in, ex, "20", ans);
        Thread t = new Thread(lift);
        t.start();
        Thread t2 = new Thread(this.input);
        t2.start();
        t2.join();
        t.join();
        assertThat(lift.getCurrentPosition(), is(0));
    }
}