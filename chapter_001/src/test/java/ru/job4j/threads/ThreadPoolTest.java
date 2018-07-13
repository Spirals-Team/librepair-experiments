package ru.job4j.threads;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Класс содержит тесты для пула потоков
 */
public class ThreadPoolTest {

    /**
     * Метод генерирует поток со слуяайным именем
     * @return
     */
    private Thread moreThreads() {
        Random rn = new Random();
        return new Thread(String.format("Thread%s",rn.nextInt(1000))) {
            @Override
            public void run() {
                try {
                    Thread.sleep(rn.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    /**
     * Метод запускает пул, генерит для него несколько работ, ожидает их окончания и останавливает пул
     *
     */
    public void testThreadPool() {
        ThreadPool test = new ThreadPool();
        Thread temp;
        test.start();
        assertThat(test.isEmpty(), is(true));
        for( int i = 0; i < 5; i++) {
            temp = moreThreads();
            System.out.println(temp.getName() + " created");
            test.add(test.new Work(temp));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(test.isEmpty(), is(true));
        test.stop();
    }

}