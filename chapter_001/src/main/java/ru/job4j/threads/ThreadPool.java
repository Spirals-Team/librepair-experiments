package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ThreadSafe
/**
 * Класс описывающий  пул потоков
 */
public class ThreadPool {
    @GuardedBy("this")
    // определяем количество доступных процессоров
    private static final int MAXPROC = Runtime.getRuntime().availableProcessors();
    // пул потоков
    private volatile List<Work> workPool = new ArrayList<>(MAXPROC);
    // буфер для потоков ожидающих освобождение пула
    private volatile Queue<Work> tempPreQueue = new LinkedList<>();
    // поток обработчик пула
    private Thread pool = new Thread() {
        @Override
        public void run() {
            addToWorkPool();
        }
    };
    // поток проверяющий окончание работы потоков в пуле
    private Thread check = new Thread() {
        @Override
        public void run() {
            checkStatus();
        }
    };

    // инициализация пула пустыми потоками
    {
        for (int i = 0; i < MAXPROC; i++) {
            workPool.add(new Work( new Thread() {
                @Override
                public void run() {
                }
            }));
        }
    }

    /**
     * Внутренний класс описывающий "работу"
     */
    class Work {
        private Thread work;
        Work (Thread w) {
            work = w;
        }
    }

    /**
     * Метод добавляет работу в буфер ожидания
     * @param work
     */
    public void add(Work work) {
        synchronized (tempPreQueue) {
            tempPreQueue.offer(work);
        }
        System.out.println(work.work.getName() + " in buffer");
    }

    /**
     * Метод стартует потоки, отвечающие за поддержку пула
     */
    public void start() {
        if (pool.getState() == Thread.State.NEW) {
            pool.start();
            check.start();
            System.out.println("Pool started");
        }
    }

    /**
     * Метод останавливет потоки, отвечающие за поддержку пула
     */
    public void stop() {
        pool.interrupt();
        check.interrupt();
        try {
            check.join();
            pool.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pool ended");
    }

    /**
     * Метод проверяет наличие в пуле работающих потоков
     * @return истина если нет работающих потоков
     */
    public boolean isEmpty() {
        return workPool.stream().anyMatch( s -> !s.work.isAlive());
    }

    /**
     * Метод проверяет есть ли потоки, закончившие работу и заменяет их новой работой из буфера
     */
    private synchronized void addToWorkPool() {
        System.out.println("pool module starting...");
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < workPool.size() && tempPreQueue.size() > 0; i++) {
                if (!workPool.get(i).work.isAlive()) {
                    workPool.set(i, tempPreQueue.poll());
                    System.out.println(workPool.get(i).work.getName() + " in pool");
                    workPool.get(i).work.start();
                    System.out.println(workPool.get(i).work.getName() + " starting");
                }
            }
            try {
                this.wait();
            } catch (InterruptedException e) {

            }
        }
        System.out.println("pool module cancels.....");
    }

    /**
     * Метод проверяет есть ли в пуле потоки закончившие работу и если есть то будит обработчик пула
     */
    private void checkStatus() {
        System.out.println("check module starting...");
        while (!Thread.currentThread().isInterrupted()) {
            if (workPool.stream().filter(s -> !s.work.isAlive()).count() > 0) {
                synchronized (this) {
                    this.notify();
                }
            }
        }
        System.out.println("check module cancels.....");
    }
}
