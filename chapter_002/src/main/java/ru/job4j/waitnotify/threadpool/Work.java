package ru.job4j.waitnotify.threadpool;

/**
 * Work.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Work implements Runnable {

    private String name;

    public Work(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.printf("start: %s%n", name);
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d%n", name, i);
        }
        System.out.printf("finish: %s%n", name);
    }
}
