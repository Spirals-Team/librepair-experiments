package ru.job4j.list;

import java.util.List;

public class ConvertList2Array {
    public int[][] twoArray(List<Integer> list, int rows) {
        int listIndex = 0;
        int amount = (int) Math.ceil((double) list.size() / (double) rows);
        int[][] array = new int[rows][amount];
        for (int rowsIndex = 0; rowsIndex < rows; rowsIndex++) {
            for (int amountIndex = 0; amountIndex < amount; amountIndex++) {
                if (listIndex < list.size()) {
                    array[rowsIndex][amountIndex] = list.get(listIndex++);
                } else {
                    array[rowsIndex][amountIndex] = 0;
                }
            }
        }
        return array;
    }
}