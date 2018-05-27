package ru.job4j.exchange;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 * */
public class SortedByHigh implements Comparator<Claim> {
    @Override
    public int compare(Claim o1, Claim o2) {
        return Float.compare(o1.getPrice(), o2.getPrice()) * -1;
    }
}
