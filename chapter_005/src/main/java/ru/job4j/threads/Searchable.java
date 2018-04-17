package ru.job4j.threads;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public interface Searchable {
    void search() throws InterruptedException;
    String getName();
}
