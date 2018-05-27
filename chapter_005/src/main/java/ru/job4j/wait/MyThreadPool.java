package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class MyThreadPool {
    private final MyThread[] threads;
    @GuardedBy("this")
    private final LinkedList<Work> queueWork = new LinkedList<>();

    public MyThreadPool() {
        int cores = Runtime.getRuntime().availableProcessors();
        threads = new MyThread[cores];
    }

    public void start() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }
    }

    public void stop() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].interrupt();
        }
    }

    public void add(Work work) {
        synchronized (queueWork) {
            queueWork.add(work);
            queueWork.notifyAll();
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                Work work = null;
                if (queueWork.size() > 0) {
                    synchronized (queueWork) {
                        work = queueWork.poll();
                    }
                }
                if (work != null) {
                    work.run();
                }
            }
        }
    }

    static class Work implements Runnable {
        @Override
        public void run() {
            System.out.println(String.format("Work do thread: %s", Thread.currentThread().getName()));
            int start = (int) (Math.random() * 100);
            int stop = start * start * start;
            int sum = 0;
            for (int i = start; i < stop; i++) {
                sum += i;
            }
            System.out.println(String.format("%s end... Start[%d], stop[%d], Sum = %d",
                    Thread.currentThread().getName(), start, stop, sum));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool pool = new MyThreadPool();
        pool.add(new Work());
        pool.add(new Work());
        pool.add(new Work());
        pool.add(new Work());
        pool.add(new Work());
        pool.add(new Work());
        pool.add(new Work());
        pool.start();
        Thread.sleep(5000);
        pool.stop();
    }
}
