package ru.job4j.threads;

import org.junit.Test;

public class ThreadPoolTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
         ThreadPool threadPool = new ThreadPool(4);
         for (int i = 0; i < 100; i++) {
             threadPool.add(threadPool.newWork());
         }
    }
}