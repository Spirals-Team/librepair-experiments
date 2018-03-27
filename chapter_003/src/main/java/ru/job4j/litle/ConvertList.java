package ru.job4j.litle;

import java.util.ArrayList;
import java.util.List;

/**
 * ConvertList.
 * @author Hincu Andrei (andreih1981@gmail.com) by 16.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ConvertList {
    /**
     * конвертирует простой массив в ArrayList.
     * @param array двумерный массив.
     * @return ArrayList.
     */
    public List<Integer> toList(int[][] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                list.add(array[i][j]);
            }
        }
        return list;
    }
    /**
     * Конвертирует ArrayList в двумерный массив.
     * @param list арайлист.
     * @param rows колличествострок.
     * @return двумерный массив.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int colum = 0;
        if (list.size() % rows == 0) {
            colum = list.size() / rows;
        } else {
            colum = list.size() / rows + 1;
        }
        int count = 0;
        int[][]array = new int[rows][colum];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colum; j++) {
                if (count < list.size()) {
                    array[i][j] = list.get(count);
                } else {
                    array[i][j] = 0;
                }
                count++;
            }
        }
        return array;
    }

    /**
     *Конвертация листа массивов в один лист Integer.
     * @param list лист массивов.
     * @return лист Integer.
     */
    public List<Integer> convert(List<int[]> list) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int[] i : list) {
            for (int j : i) {
                result.add(j);
            }
        }
        return result;
    }

    /**
     * Конвертация листа с null в массив.
     * @param list list.
     * @return массив.
     */
    public int[] convert1(List<Integer> list) {
        int[]array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                array[i] = 0;
            } else {
                array[i] = list.get(i);
            }
        }
        return array;
    }

}
