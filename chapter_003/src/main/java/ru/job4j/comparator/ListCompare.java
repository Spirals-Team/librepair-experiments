package ru.job4j.comparator;

import java.util.*;

public class ListCompare implements Comparator<List<Integer>> {
    @Override
    public int compare(List<Integer> left, List<Integer> right) {
        int result = 0;
        if (left.size() != right.size()) {
            result = Integer.compare(right.size(), left.size());
        } else {
            Iterator<Integer> it1 = left.iterator();
            Iterator<Integer> it2 = right.iterator();
            while (it1.hasNext()) {
                Integer elem1 = it1.next();
                Integer elem2 = it2.next();
                result = compare(elem1, elem2);
                if (compare(elem1, elem2) != 0) {
                    break;
                }
            }
        }
        return result;
    }

    public int compare(Integer o1, Integer o2) {
        return Integer.compare(o1, o2);
    }
}
