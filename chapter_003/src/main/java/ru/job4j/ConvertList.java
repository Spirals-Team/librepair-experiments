package ru.job4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ConvertList {

    public List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int[] out : array) {
            for (int in : out) {
                list.add(in);
            }
        }
        return list;
    }

    public int[][] toArray(List<Integer> list, int rows) {
        int size = list.size();
        int column = size % rows == 0 ? size / rows : size / rows + 1;
        int[][] curr = new int[column][rows];
        int out = 0;
        int in  = 0;
        for (int x : list) {
            if (in == rows) {
                in = 0;
                curr[++out][in++] = x;
            } else {
                curr[out][in++] = x;
            }
        }
        return curr;
    }
}
