package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

public class ConvertList2Array {


    public int[][] toArray(List<Integer> list, int rows) {
        int cells = list.size() % rows != 0 ? (list.size() / rows) + 1 : list.size() / rows;
        int[][] array = new int[rows][cells];
        int count = 0;
        int index = 0;
        for (Integer in : list) {
            array[count][index++] = in;
            if (index == cells) {
                index = 0;
                count++;
                }
        }
        return array;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] in: list) {
            for (int out: in) {
                result.add(out);
            }

        }
        return result;
    }
    }
