package ru.job4j.collections;

import java.util.*;

public class TestingThePerformanceOfCollections {

    public long add(Collection<String> collection, int amount) {

        long start = System.currentTimeMillis();

        for (int ind = 0; ind < amount; ind++) {
            collection.add(String.valueOf(ind));
        }

        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;

        return timeConsumedMillis;
    }

    public long delete(Collection<String> collection, int amount) {

        long start = System.currentTimeMillis();

        for (int ind = 0; ind < amount; ind++) {
            collection.remove(String.valueOf(ind));
        }

        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;

        return timeConsumedMillis;
    }

}
