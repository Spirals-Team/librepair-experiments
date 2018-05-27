package ru.job4j.ioc.demo;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MemoryStorage implements Storage {
    @Override
    public void add(User user) {
        System.out.println("Add user in memory");
    }
}
