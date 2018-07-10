package ru.job4j.waitnotify.threadpool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * ThreadPool.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 2
 */
@ThreadSafe
public class ThreadPool {

    private final BlockingQueue<Runnable> workQueue;
    private final int size;
    @GuardedBy("this")
    private boolean isStopped = false;
    private final List<RunningThread> runningThreads = new ArrayList<>();

    public ThreadPool(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        this.size = Runtime.getRuntime().availableProcessors();
    }

    public void init() {
        for (int i = 0; i < this.size; i++) {
            RunningThread t = new RunningThread();
            t.start();
            this.runningThreads.add(t);
        }
    }

    /**
     * Add work to wait queue.
     *
     * @param work
     */
    public void add(Work work) {
        synchronized (this) {
            if (isStopped) {
                throw new IllegalStateException();
            }
        }
        workQueue.offer(work);
    }

    /**
     * Stop thread pool.
     */
    public void stop() {
        synchronized (this) {
            this.isStopped = true;
        }
        for (RunningThread thread : runningThreads) {
            thread.stopThread();
        }

    }

    /**
     * Wrapper for runnable object.
     */
    private class RunningThread extends Thread {

        private boolean isThreadStopped = false;

        @Override
        public void run() {
            while (!this.isInterrupted()
                && (!isThreadStopped || !workQueue.isEmpty())) {
                Runnable runnable;
                while ((runnable = pollWork()) != null) {
                    runnable.run();
                }
            }
        }

        private Runnable pollWork() {
            try {
                return workQueue.poll(500L, MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void stopThread() {
            this.isThreadStopped = true;
            this.interrupt();
        }
    }
}
