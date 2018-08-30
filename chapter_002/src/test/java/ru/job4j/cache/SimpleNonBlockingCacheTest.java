package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleNonBlockingCacheTest {

    private SimpleNonBlockingCache<Integer, User> cache;

    @Test
    public void addItemToCacheThenSizeOfCacheIncreased() {
        cache = new SimpleNonBlockingCache<>();
        cache.add(1, new User(1, "User1"));
        assertThat(cache.size(), is(1));
        cache.add(2, new User(2, "User2"));
        assertThat(cache.size(), is(2));
    }

    @Test
    public void deleteItemFromCacheThenSizeOfCacheDecreased() {
        cache = new SimpleNonBlockingCache<>();
        cache.add(1, new User(1, "User1"));
        cache.add(2, new User(2, "User2"));
        cache.delete(1);
        assertThat(cache.size(), is(1));
    }

    @Test
    public void updateItemInCacheThenItemVersionIsIncreased() {
        cache = new SimpleNonBlockingCache<>();
        cache.add(1, new User(1, "User1"));
        assertThat(cache.get(1).getVersion(), is(0));
        cache.update(1, new User(1, "User2"));
        assertThat(cache.get(1).getVersion(), is(1));
    }
}