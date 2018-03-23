package ru.job4j.jmm;

public class MemoryConsistencyError extends Thread {
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    public void run() {
        for (int x = 0; x < 100_000_000; x++) {
            counter++;
        }
    }

    public void execute() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            MemoryConsistencyError mce = new MemoryConsistencyError();
            mce.start();
        }
        Thread.sleep(1000);
    }
}
