package ru.job4j.threads;

public class Time implements Runnable {
    private int time;
    private Thread thread;

    public Time(int time, Thread thread) {
        this.time = time;
        this.thread = thread;
    }

    @Override
    public void run() {
        System.out.println("Start Thread Time");
        try {
            Thread.sleep(time);
            if (thread.isAlive()) {
                thread.interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End Thread Time");
    }
}
