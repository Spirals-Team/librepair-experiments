package ru.job4j.nonBlockingCache;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class CacheTest {
    private Cache cache = new Cache();

    @Test
    public void firstTest() throws InterruptedException {
        Thread one = new Thread() {
            @Override
            public void run() {
                cache.add(1, new User("first", 1));
            }
        };
        Thread two = new Thread() {
            @Override
            public void run() {
                cache.add(2, new User("second", 2));
            }
        };

        one.start();
        two.start();
        one.join();
        two.join();

        assertThat(cache.getValue(1).getName(), is("first"));
        assertThat(cache.getValue(2).getName(), is("second"));
        cache.update(2, new User("two", 2));
        assertThat(cache.getValue(2).getName(), is("two"));
    }
}