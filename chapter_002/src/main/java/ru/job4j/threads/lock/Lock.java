package ru.job4j.threads.lock;

/**
 * Lock for controlling access to a shared resource by
 * multiple threads.
 */
public interface Lock {

    /**
     * Lock resource.
     *
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException;

    /**
     * Unlock resource
     */
    public void unlock();
}
