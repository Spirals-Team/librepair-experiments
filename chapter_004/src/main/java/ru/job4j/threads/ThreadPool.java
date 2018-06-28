package ru.job4j.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private  int n;
    private Work[] threads;
    private BlockingQueue queue;

    public ThreadPool(int n) {
        this.n = n;
        queue = new LinkedBlockingQueue();
        threads = new Work[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Work();
            threads[i].start();
        }
    }

    public void add(Work work) {
        synchronized (queue) {
            try {
                queue.put(work);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.notifyAll();
        }
    }

    public Work newWork() {
        return new Work();
    }

    public class Work extends Thread {

        @Override
        public void run() {
            Runnable r;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println(Thread.currentThread().getName());
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        r = (Runnable) queue.take();
                        r.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
