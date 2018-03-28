package ru.job4j.waitNotify;

import org.junit.Test;

public class ThreadPoolTest {
    ThreadPool pool = new ThreadPool(4, 1);
    @Test
    public void firstTest() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        };
        try {
            pool.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
