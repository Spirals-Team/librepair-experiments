package ru.job4j.waitnotifynotifyall.examples;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {

    private List queue = new LinkedList();
    private int  limit = 10;

    public BlockingQueue(int limit) {
        this.limit = limit;
    }


    public synchronized void put(T item) throws InterruptedException  {
        System.out.println("[BlockingQueue] try to put: " + item);
        while (this.queue.size() == this.limit) {
            System.out.println("[BlockingQueue] queue is full, waiting until space is free");
            wait();
        }

//        почему в методе put потоки уведомляются только когда очередь пуста? Какой в этом смысл? И соответственно наоборот в метода take.
//        if (this.queue.size() == 0) {
//            System.out.println("[BlockingQueue] queue is empty, notify");
            notifyAll();
//        }
        this.queue.add(item);
        System.out.println("[BlockingQueue] put ok: " + item);
    }


    public synchronized T take() throws InterruptedException {
        System.out.println("[BlockingQueue] try to take");
        while (this.queue.size() == 0) {
            System.out.println("[BlockingQueue] queue is empty, waiting until smth is put");
            wait();
        }
//        почему в методе put потоки уведомляются только когда очередь пуста? Какой в этом смысл? И соответственно наоборот в метода take.
//        if (this.queue.size() == this.limit) {
//            System.out.println("[BlockingQueue] queue is full, notify");
            notifyAll();
//        }

        T item = (T) this.queue.remove(0);
        System.out.println("[BlockingQueue] take ok: " + item);
        return item;
    }
}
