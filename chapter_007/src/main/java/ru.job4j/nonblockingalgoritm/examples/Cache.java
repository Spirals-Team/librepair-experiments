package ru.job4j.nonblockingalgoritm.examples;

import java.util.concurrent.ConcurrentHashMap;
//https://eclipsesource.com/blogs/2012/07/09/building-lightweight-in-memory-caches-with-google-guava-no-more-putifabsent/
public class Cache {

    private static final long MAX_SIZE = 100;

    private final ConcurrentHashMap map;

    public Cache() {
        map = new ConcurrentHashMap();
    }

    public String getEntry(String key) {
        String result = createChacheEntry(key);
        removeOldestCacheEntryIfNecessary();
        return result;
    }

    private String createChacheEntry(String key) {
        String result = (String) map.get(key);
        if (result == null) {
            String putResult = (String) map.putIfAbsent(key, createRandom());
            if (putResult != null) {
                result = putResult;
            }
        }
        return result;
    }

    private void removeOldestCacheEntryIfNecessary() {
        if (map.size() > MAX_SIZE) {
            String keyToDelete = (String) map.keys().nextElement(); // very effective 😉
            map.remove(keyToDelete);
        }
    }

    private String createRandom() {
        return "I'm a random string or resource... Be creative ;)";
    }
}
