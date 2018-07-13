package ru.job4j.threads;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPool2Test {
    /**
     * Метод генерирует поток со слуяайным именем и случайной длительности
     * @return поток
     */
    private Thread moreThreads() {
        Random rn = new Random();
        return new Thread(String.format("Thread%s",rn.nextInt(1000))) {
            @Override
            public void run() {
                try {
                    Thread.sleep(rn.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    /**
     * Метод запускает пул, генерит для него несколько работ и останавливает пул
     *
     */
    public void testThreadPool() {
        ThreadPool2 test = new ThreadPool2();
        Thread temp;
        for( int i = 0; i < 5; i++) {
            temp = moreThreads();
            System.out.println(temp.getName() + " created");
            test.add( new ThreadPool2.Work(temp));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(test.isEmpty(), is(false));
        test.stop();
    }

}