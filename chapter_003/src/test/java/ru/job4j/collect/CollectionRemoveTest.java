package ru.job4j.collect;

import org.junit.Test;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeSet;

public class CollectionRemoveTest {
    @Test
    public void listRemoveTest() {
        Collect collect = new Collect();
        LinkedList<String> list = new LinkedList<>();
        collect.add(list, 100000);
        long time = collect.delete(list,10000);
        System.out.println(time);
    }

    @Test
    public void arrayRemoveTest() {
        Collect collect = new Collect();
        ArrayList<String> array = new ArrayList<>();
        collect.add(array, 100000);
        long time = collect.delete(array,10000);
        System.out.println(time);
    }

    @Test
    public void setRemoveTest() {
        Collect collect = new Collect();
        TreeSet set = new TreeSet<>();
        collect.add(set, 100000);
        long time = collect.delete(set,10000);
        System.out.println(time);
    }
}
