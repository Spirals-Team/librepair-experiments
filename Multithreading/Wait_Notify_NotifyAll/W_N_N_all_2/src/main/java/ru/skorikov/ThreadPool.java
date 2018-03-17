package ru.skorikov;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 22.01.18
 * @ version: java_kurs_standart
 */
@ThreadSafe
public class ThreadPool {
    /**
     * Количество ядер.
     */
    private static int n = Runtime.getRuntime().availableProcessors();
    /**
     * Массив потоков.
     */
    private final Job[] threads;

    /**
     * Лист задач.
     */
    @GuardedBy("itself")
    private final LinkedList<Work> list;

    /**
     * Конструктор.
     */
    public ThreadPool() {
        this.list = new LinkedList<>();
        this.threads = new Job[n];
    }

    /**
     * Добавить задачу в лист задач.
     *
     * @param work задача.
     */
    public void add(Work work) {
        synchronized (list) {
            list.add(work);
            list.notifyAll();
        }
    }

    /**
     * Класс поток работа.
     * запускает задачи.
     */
    private class Job extends Thread {
        @Override
        public void run() {
            Runnable runnable;
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (list) {
                    while (list.size() == 0) {
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    runnable = list.remove(0);
                }
                runnable.run();
            }
        }
    }

    /**
     * Запуск пула потоков.
     */
    public void startPool() {
        for (int i = 0; i < n; i++) {
            threads[i] = new Job();
            threads[i].start();
        }
    }

    /**
     * Остановить пул потоков.
     */
    public void stopPool() {
        for (int i = 0; i < n; i++) {
            threads[i].interrupt();
        }
    }
}
