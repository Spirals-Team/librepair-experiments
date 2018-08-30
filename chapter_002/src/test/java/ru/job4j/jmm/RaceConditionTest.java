package ru.job4j.jmm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RaceConditionTest {

    @Test
    public void showSharedVisibilityProblem() throws InterruptedException {
        System.out.println("Start");
        List<Thread> list = new ArrayList<>();
        SharedObject so = new SharedObject(0L);
        for (int i = 0; i < 200; i++) {
            Thread t = new Thread(new RaceCondition(1000, so));
            list.add(t);
            t.start();
        }
        for (Thread t : list) {
            t.join();
        }
        System.out.printf("Expected: 200000, but was: %d%n", so.getNum());
    }
}