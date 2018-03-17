package ru.job4j.multithreading.poducer;

import java.util.concurrent.SynchronousQueue;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        /**
         * Специальная коллекция которая блокирует нить после вложения до тех пор пока другая нить не заберет элемент из очереди
         * после этого вторая нить тоже будет ждать пока первая не добавит снова элемент.
         */
        final SynchronousQueue<String> queue = new SynchronousQueue<>(true);

        Thread producer = new Thread("Producer") {
            @Override
            public void run() {
                String product = "Продукт.";
                try {
                    queue.put(product);
                    System.out.printf("[%s] произвел %s", Thread.currentThread().getName(), product);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        producer.start();
    final Thread cunsomer = new Thread("Потребитель") {
        @Override
        public void run() {
            try {
                String product = queue.take();
                System.out.printf("[%s] забрал со склада %s", Thread.currentThread().getName(), product);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    cunsomer.start();
    }
}
