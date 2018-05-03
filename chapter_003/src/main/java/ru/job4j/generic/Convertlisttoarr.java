package ru.job4j.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Конвертация листа массивов в один лист Integer
 */

public class Convertlisttoarr {

    /**
     * @param list
     * @return
     */
    public ArrayList<Integer> convert(ArrayList<int[]> list) {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (int[]k:list) {
            for (Integer n:k) {
                returnList.add(n);
            }
        }
        return returnList;
    }
}
