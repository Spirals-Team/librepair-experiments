package ru.job4j.collect;
import org.junit.Test;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeSet;

public class CollectionAddTest {
    @Test
    public void listAddTest() {
        Collect collect = new Collect();
        LinkedList<String> list = new LinkedList<>();
        long time = collect.add(list, 100000);
        System.out.println(time);
    }

    @Test
    public void arrayAddTest() {
        Collect collect = new Collect();
        ArrayList<String> array = new ArrayList<>();
        long time = collect.add(array, 100000);
        System.out.println(time);
    }

    @Test
    public void setAddTest() {
        Collect collect = new Collect();
        TreeSet set = new TreeSet<>();
        long time = collect.add(set, 100000);
        System.out.println(time);
    }
}
