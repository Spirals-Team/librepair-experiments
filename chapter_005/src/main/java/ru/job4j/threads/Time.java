package ru.job4j.threads;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Time implements Runnable {
    private long period;
    private Thread thread;
    private long startTime;

    public Time(long period) {
        this.period = period;
        thread = new Thread(this);
        System.out.println("Таймер - ЗАПУСК");
        thread.start();
        startTime = System.currentTimeMillis();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        while (this.getThread().isAlive()) {
            if (System.currentTimeMillis() - startTime > period) {
                break;
            }
        }
        System.out.println("Время истекло");
    }
}
