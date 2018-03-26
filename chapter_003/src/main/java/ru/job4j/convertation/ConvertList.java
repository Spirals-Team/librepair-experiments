package ru.job4j.convertation;

import java.util.ArrayList;
import java.util.List;

public class ConvertList {
    public ArrayList<Integer> toList(int[][] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                list.add(array[i][j]);
            }
        }
        return list;
    }

    public int[][] toArray(ArrayList<Integer> list, int rows) {
        int[][] array = new int[rows][rows];
        int mod = list.size() % rows;
        for (int i = 0; i < mod; i++) {
            list.add(0);
        }
        int[] arr = list.stream().mapToInt(i->i).toArray();
        int temp = -1;
        for (int i = 0; i < arr.length; i++) {
            if ((i % rows) == 0)
                temp++;
            array[temp][i % rows] = arr[i];
        }
        return array;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            for (Integer elem : list.get(i)) {
                result.add(elem);
            }
        }
        return result;
    }
}
