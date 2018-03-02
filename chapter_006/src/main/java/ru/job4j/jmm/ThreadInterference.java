package ru.job4j.jmm;

public class ThreadInterference {
    private Counter counter;

    public ThreadInterference(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return counter;
    }

    public void execute() throws InterruptedException {
        Thread threadIncrement = new Thread(() -> {
            for (double i = 0; i < 100_000_000; i++) {
                counter.increment();
            }
        });
        Thread threadDecrement = new Thread(() -> {
            for (double i = 0; i < 100_000_000; i++) {
                counter.decrement();
            }
        });
        threadIncrement.start();
        threadDecrement.start();

        threadIncrement.join();
        threadDecrement.join();
    }
}
