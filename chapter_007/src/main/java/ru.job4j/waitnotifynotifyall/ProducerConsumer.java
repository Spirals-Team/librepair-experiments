package ru.job4j.waitnotifynotifyall;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue queue = new BlockingQueue(5);

        new Thread(new Producer(queue)).start();

//        Thread.currentThread().sleep(1000);
//
        new Thread(new Consumer(queue)).start();
    }

    static class Producer implements Runnable {

        private final BlockingQueue queue;

        Producer(BlockingQueue queue) {

            this.queue = queue;
        }

        @Override
        public void run() {

            System.out.println("Producer run");

            while (true) {

                try {
                    queue.put(produce());

                    Thread.currentThread().sleep(200);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }

        }

        private Integer produce() {

            Integer i = new Random().nextInt(100);

            System.out.println("Producer produce: " + i);

            return i;
        }
    }

    static class Consumer implements Runnable {

        private final BlockingQueue queue;

        Consumer(BlockingQueue queue) {

            this.queue = queue;
        }

        @Override
        public void run() {

            System.out.println("Consumer run");

            while (true) {

                try {

                    consume();

                    Thread.currentThread().sleep(1500);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }
            }

        }

        private void consume() throws InterruptedException {

            Integer i = (Integer) queue.take();

            System.out.println("Consumer consumed: " + i);
        }

    }
}

class BlockingQueue<T> {

    private List queue = new LinkedList();

    private int limit = 10;

    public BlockingQueue(int limit) {

        this.limit = limit;
    }

    public synchronized void put(T item) throws InterruptedException {

        System.out.println("BlockingQueue try to put: " + item + " (method put)");

        while (this.queue.size() == this.limit) {

            System.out.println("BlockingQueue queue is full, waiting until space is free (method put)");

            wait();
        }

//        WORK ON BUGS почему в методе put потоки уведомляются только когда очередь пуста? Какой в этом смысл? И соответственно наоборот в метода take.
//        if (this.queue.size() == 0) {
//            System.out.println("BlockingQueue queue is empty, notifyAll (method put)");
        notifyAll();
//        }

        this.queue.add(item);

        System.out.println("BlockingQueue put ok: " + item + " (method put)");

    }

    public synchronized T take() throws InterruptedException {

        System.out.println("BlockingQueue try to take (method take)");

        while (this.queue.size() == 0) {

            System.out.println("BlockingQueue queue is empty, waiting until smth is put (method take)");

            wait();
        }

//       WORK ON BUGS в методе put потоки уведомляются только когда очередь пуста? Какой в этом смысл? И соответственно наоборот в метода take.
//        if (this.queue.size() == this.limit) {
//            System.out.println("BlockingQueue queue is full, notify (method take)");
        notifyAll();
//        }

        T item = (T) this.queue.remove(0);

        System.out.println("BlockingQueue take ok: " + item + "method take");

        return item;
    }




}
