package ru.job4j.lite;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetExample {

//    class TreeNode {
//        TreeNode left;
//        TreeNode right;
//    }

    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>(); //HashSet

        set.add(3);
        set.add(1);
        set.add(4);
        set.add(0);

        set.add(4);

        for (Integer value : set) {
            System.out.println(value);
        }
    }
}
