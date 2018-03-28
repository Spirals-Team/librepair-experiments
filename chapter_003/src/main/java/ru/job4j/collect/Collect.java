package ru.job4j.collect;

import java.util.Collection;
import java.util.UUID;

public class Collect {
    public long add(Collection<String> collection, int amount) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.add(UUID.randomUUID().toString());
        }
        return System.currentTimeMillis() - startTime;
    }

    public long delete(Collection<String> collection, int amount) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.remove(collection.iterator().next());
        }
        return System.currentTimeMillis() - startTime;
    }
}
