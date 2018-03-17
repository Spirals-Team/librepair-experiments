package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static java.lang.Thread.State.RUNNABLE;
import static java.lang.Thread.State.WAITING;
import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 22.01.18
 * @ version: java_kurs_standart
 */
public class StartTest {
    /**
     * Пробуем добавить поток в очередь.
     *
     * @throws InterruptedException исключение.
     */
    @Test
    public void whenAddThreadThenReturnThreadName() throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(1);
        Thread thread1 = new Thread(new Producer(queue));
        thread1.start();

        Assert.assertThat(queue.take(), is(thread1.getName()));
    }
    /**
     * Пробуем добавить поток в нулевую очередь.
     *
     * @throws InterruptedException исключение.
     */
    @Test
    public void whenAddToNULLQueue() throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(0);
        Thread thread1 = new Thread(new Producer(queue));
        thread1.start();

        Assert.assertThat(thread1.getState(), is(RUNNABLE));
    }
    /**
     * Пробуем получить что то из очереди.
     *
     * @throws InterruptedException исключение.
     */
    @Test
    public void tryGetSomeThingFromQueue() throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(1);
        Thread thread1 = new Thread(new Producer(queue));
        thread1.start();
        thread1.join();
        Consumer consumer = new Consumer(queue);
        consumer.run();

        Assert.assertNotNull(consumer.getData());
    }
    /**
     * Пробуем добавить несколько потоков в очередь.
     *
     * @throws InterruptedException исключение.
     */
    @Test
    public void tryAddTwoThread() throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(2);
        Thread thread1 = new Thread(new Producer(queue));
        thread1.start();
        thread1.join();
        Thread thread2 = new Thread(new Producer(queue));
        thread2.start();
        thread2.join();
        Thread thread3 = new Thread(new Producer(queue));
        thread3.start();
        thread3.join(1000);

        Assert.assertThat(thread3.getState(), is(WAITING));
    }
}