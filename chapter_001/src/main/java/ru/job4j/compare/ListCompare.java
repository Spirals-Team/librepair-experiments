package ru.job4j.compare;

import java.util.Comparator;
import java.util.List;

/**
 * Класс сравнения двух списков.
 */
public class ListCompare implements Comparator<List<Integer>> {

    /**
     * Метод сравнения двух списков.
     * @param left первый список
     * @param right второй список
     * @return результат сравнения
     */
    @Override
    public int compare(final List<Integer> left, final List<Integer> right) {
        Integer lz = left.size();
        Integer rz = right.size();
        Integer lg = 0;
        Integer rg = 0;
        for (int i =  0; lz == rz && i < lz; i++) {
            lg = left.get(i);
            rg = right.get(i);
            if (lg != rg) {
                break;
            }
        }
        return lz != rz ? lz.compareTo(rz) : lg.compareTo(rg);
    }
}
