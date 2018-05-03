package ru.job4j.startcollection;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CollectionSet {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>(); //пихает в рандомном порядке
        set.add(15);
        set.add(1);
        set.add(4);
        set.add(3);
        set.add(2);
        for (Integer value:set) {
            System.out.println(value);

        }
        Set<Integer> sortSet = new TreeSet<Integer>(); //100% отсортированный
        sortSet.add(3);
        sortSet.add(1);
        sortSet.add(4);
        sortSet.add(15);
        sortSet.add(2);
        for (Integer value2:sortSet) {
            System.out.println(value2);

        }

    }
}
