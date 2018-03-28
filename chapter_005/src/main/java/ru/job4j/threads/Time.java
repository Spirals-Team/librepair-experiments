package ru.job4j.threads;

public class Time implements Runnable {
    private long startTime;
    private long maxTime;
    private Thread thread;

    public Time(long startTime, long maxTime, Thread thread) {
        this.startTime = startTime;
        this.maxTime = maxTime;
        this.thread = thread;
    }

    @Override
    public void run() {
        thread.start();
        if ((System.currentTimeMillis() - startTime) > maxTime) {
            Thread.currentThread().interrupt();
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threadCount = new Thread(new CountChar("Some string with"));
        Thread threadTime = new Thread(new Time(System.currentTimeMillis(), 1, threadCount));

        threadTime.start();
        threadTime.join();
    }
}
