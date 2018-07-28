package ru.job4j.comparator;

import java.util.Comparator;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ListCompare implements Comparator<List<Integer>> {
    @Override
    public int compare(List<Integer> left, List<Integer> right) {
        int result = 0;
        if (left.size() != right.size()) {
            result = left.size() < right.size() ? -1 : -2;
        } else {
            for (int i = 0; i < left.size(); i++) {
                int com = left.get(i).compareTo(right.get(i));
                if (com != 0) {
                    result = com > 0 ? 1 : 2;
                } else {
                    result = 0;
                }
            }
        }
        return result;
    }
}
