package ru.job4j.wait;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Producer<T> extends Thread {
    private SimpleBlockingQueue<T> resource;
    private T[] value;

    public Producer(SimpleBlockingQueue<T> resource, T[] value) {
        this.resource = resource;
        this.value = value;
        start();
    }

    @Override
    public void run() {
        for (T item : value) {
            resource.offer(item);
        }
    }
}
