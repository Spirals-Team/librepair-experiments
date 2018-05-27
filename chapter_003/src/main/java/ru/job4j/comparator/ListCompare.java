package ru.job4j.comparator;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ListCompare implements Comparator<List<Integer>> {
    @Override
    public int compare(List<Integer> left, List<Integer> right) {
        Iterator iterL = left.iterator();
        Iterator iterR = right.iterator();
        int rezult = 0;
        do {
            if (iterL.hasNext() && !iterR.hasNext()) {
                rezult = 1;
                break;
            } else if (!iterL.hasNext() && iterR.hasNext()) {
                rezult = -1;
                break;
            }
            rezult = Integer.compare((int) iterL.next(), (int) iterR.next());
            if (rezult != 0) {
                break;
            }
        } while (iterL.hasNext() || iterR.hasNext());
        return rezult;
    }

    public static void main(String[] args) {
        ListCompare listCompare = new ListCompare();
        System.out.println(listCompare.compare(Arrays.asList(1, 3), Arrays.asList(1, 2)));
    }
}
