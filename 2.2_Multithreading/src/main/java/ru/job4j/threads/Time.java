package ru.job4j.threads;

/**
 * Class Time.
 */
public class Time implements Runnable {
    /**
     * @param thread stopping thread
     */
    private Thread thread;

    /**
     * Constructor.
     * @param thread stopping thread
     */
    public Time(Thread thread) {
        this.thread = thread;
    }

    /**
     * method run.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}