package ru.skorikov;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 08.02.18
 * @ version: java_kurs_standart
 */
public class ThreadPoolTest {
    /**
     * Создадим новый пул потоков.
     */
    @Test
    public void tryCreateNewThreadPool() {
        ThreadPool threadPool = new ThreadPool();
        Work work = new Work();
        for (int i = 0; i < 1000; i++) {
            threadPool.add(work);
        }
        threadPool.startPool();
        threadPool.stopPool();
    }
}