package ru.job4j.waitnotify.threadpool;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ThreadPoolTest {

    @Test
    public void testThreadPool() throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        ThreadPool pool = new ThreadPool(workQueue);
        pool.init();
        for (int i = 0; i < 100; i++) {
            pool.add(new Work("work" + i));
        }
        Thread.sleep(10000L);
        pool.stop();
    }
}