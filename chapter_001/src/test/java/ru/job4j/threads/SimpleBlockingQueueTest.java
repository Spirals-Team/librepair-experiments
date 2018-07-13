package ru.job4j.threads;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    /**
     * Тест запускает Производителя  в одном потоке и генерит 20 товаров в стеке через случайное время
     * Продавец во втором потоке забирает товара по мере их появления
     */
    public void testBlockingQueue() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread threadP = new Thread() {
            Random rn = new Random();
            @Override
            public void run() {
                int off;
                for (int i = 0; i < 20; i++) {
                    off = rn.nextInt(1000);
                    System.out.println(String.format(" Offer %s", off));
                    queue.offer(off);
                    try {
                        Thread.sleep(rn.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread threadC = new Thread() {
            @Override
            public void run() {
                int off;
                for (int i = 0; i < 20; i++) {
                    off = queue.poll();
                    System.out.println(String.format(" Poll %s", off));
                }
            }
        };
        threadP.start();
        threadC.start();
        try {
            threadP.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            threadC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(threadP.getPriority(), is(5));
    }
}