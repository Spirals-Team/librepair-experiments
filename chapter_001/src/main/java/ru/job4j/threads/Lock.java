package ru.job4j.threads;

/**
 * Класс реализует механизм Lock
 */
public class Lock {
    // флаг блокировки
    private volatile boolean lock = false;
    // поток который поставил блокировку
    private volatile Thread lockThread = null;

    /**
     * Метод блокировки потока если флаг установлен другим потоком
     * если флаг не установлен то он устанавливается
     */
    public void lock() {
        while (lock && !Thread.currentThread().equals(lockThread)) {
            System.out.println(String.format("Thread %s blocking..",Thread.currentThread().getName()));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("Thread %s is lock",Thread.currentThread().getName()));
        lock = true;
        lockThread = Thread.currentThread();
    }

    /**
     * Метод снимает флаг если он был установлен текущим потоком
     */
    public void unlock() {
        if (Thread.currentThread().equals(lockThread)) {
            System.out.println(String.format("Thread %s is unlock..",Thread.currentThread().getName()));
            lock = false;
            lockThread = null;
        }
    }

    /**
     * Метод проверяет флаг блокировки
     * @return результат
     */
    public boolean isLock() {
        return lock;
    }

}
