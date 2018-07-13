package ru.job4j.threads;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тест для задачи Switcher
 */
public class SwitcherTest {

    @Test
    public void testSwitch() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Switcher sw = new Switcher();

        pool.execute(sw.thread1);
        pool.execute(sw.thread2);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdownNow();
        assertThat(pool.isShutdown(), is(true));
        try {
            pool.awaitTermination(15000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Result:");
        System.out.println(sw.getString());
    }
}