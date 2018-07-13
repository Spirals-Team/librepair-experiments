package ru.job4j.threads;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Класс реализует простейший функционал пула потоков
 */
public class ThreadPool2 {

    // определяем сколько у нас процессоров в системе
    private static final int MAXPROC = Runtime.getRuntime().availableProcessors();
    // пул потоков
    private final ArrayBlockingQueue<Work> workPool = new ArrayBlockingQueue(MAXPROC);
    // буфер для потоков ожидающих освобождение пула
    private final LinkedBlockingQueue<Work> tempPreQueue = new LinkedBlockingQueue<>();

    /**
     * Поток - обработчик пула
     */
    private final Thread runPool = new Thread() {
        @Override
        public void run() {
            System.out.println("trans module starting...");
            Work work = null;
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    work = tempPreQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    continue;
                }
                System.out.println(String.format("%s in pool", work.work.getName()));
                try {
                    workPool.put(work);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    continue;
                }
                System.out.println(String.format("%s running", work.work.getName()));
                work.work.start();
            }
            System.out.println("trans module ended...");
        }
    };

    /**
     * Поток для очистки пула от завершенных процессов
     */
    private final Thread clearPool = new Thread() {
        @Override
        public void run() {
            Optional<Work> work;
            System.out.println("clear module starting...");
            while (!Thread.currentThread().isInterrupted()) {
                work = workPool.stream().filter(s -> s.work.getState()== State.TERMINATED).findFirst();
                if (work.isPresent() && workPool.remove(work.get())) {
                    System.out.println(String.format("%s ended", work.get().work.getName()));
                }
            }
            System.out.println("clear module ended...");
        }
    };

    /**
     * Конструктор с запуском базовых потоков
     */
    ThreadPool2() {
        System.out.println("Pool starting");
        runPool.start();
        clearPool.start();
    }

    /**
     * Статический внутренний класс для класса работа
     */
    static class Work {
        private Thread work;

        Work (Thread w) {
            work = w;
        }
    }

    /**
     * Метод добавляет работу в буфер
     * @param work работа
     */
    public void add(final Work work) {
        System.out.println(String.format("%s in buffer", work.work.getName()));
        tempPreQueue.offer(work);
    }

    /**
     * Метод ожидает окончания работ в пуле и останавливет потоки,  отвечающие за поддержку пула
     */
    public void stop() {
        while (!isEmpty()) {

        }
        runPool.interrupt();
        clearPool.interrupt();
        try {
            runPool.join();
            clearPool.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Pool ended");
    }

    /**
     * Метод возвращает истину если пул и буфер пусты
     */
    public boolean isEmpty() {
        return workPool.remainingCapacity() == MAXPROC && tempPreQueue.isEmpty();
    }
}
