package ru.job4j.waitnotifynotifyall;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ThreadPool {
    @GuardedBy("taskPool")
    private final Queue<Runnable> taskPool = new LinkedList<>();
    private List<Task> threadPool = new LinkedList<>();
    private boolean isStopped = false;

    public ThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            threadPool.add(new Task());
        }
        for (Thread thread : threadPool) {
            thread.start();
        }
    }

    public void add(Runnable work) {
        synchronized (taskPool) {
            taskPool.offer(work);
            taskPool.notify();
        }
    }

    public void stop(int time) {
        while (!taskPool.isEmpty()) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isStopped = true;
        synchronized (taskPool) {
            taskPool.notifyAll();
        }
    }

    private class Task extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (!isStopped) {
                synchronized (taskPool) {
                    while (taskPool.isEmpty() && !isStopped) {
                        try {
                            taskPool.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = taskPool.poll();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
