package ru.job4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ConvertList {

    public List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int[] x : array) {
            for (int item : x) {
                list.add(item);
            }
        }
        return list;
    }

    public int[][] toArray(List<Integer> list, int rows) {
        while (list.size() % rows != 0) {
            list.add(0);
        }
        int column = list.size() / rows;
        int[][] newArray = new int[rows][column];
        int x = 0;
        int y = 0;
        for (int item: list) {
            if (y == column) {
                y = 0;
                x++;
            }
            newArray[x][y] = item;
            y++;
        }
        return newArray;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> newList = new ArrayList<Integer>();
        for (int[] iter : list) {
            for (int item : iter) {
                newList.add(item);
            }
        }
        return newList;
    }
}
