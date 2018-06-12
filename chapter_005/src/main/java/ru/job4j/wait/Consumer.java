package ru.job4j.wait;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Consumer<T> extends Thread {
    private SimpleBlockingQueue<T> resource;
    private int number;

    public Consumer(SimpleBlockingQueue<T> resource, int number) {
        this.resource = resource;
        this.number = number;
        start();
    }

    @Override
    public void run() {
        for (int i = 0; i < number; i++) {
            resource.poll();
        }
    }
}
