package ru.job4j.convertlist;

import java.util.ArrayList;
import java.util.List;

/**
 * ConvertList.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ConvertList {
    /**
     * Array to convert to list.
     * @param array - array
     * @return - list
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> intList = new ArrayList<>();
        for (int[] row : array) {
            for (int col : row) {
                intList.add(col);
            }
        }
        return intList;
    }
    /**
     * List to convert to array.
     * @param list - list
     * @param rows - rows
     * @return - array
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int columns = ((list.size()) % rows == 0) ? list.size() / rows : (list.size() / rows) + 1;
        int[][] intArr = new int[rows][columns];
        int row = 0; int col = 0;
        for (Integer val : list) {
            intArr[row][col] = val;
            if (col < columns - 1) {
                col++;
            } else {
                col = 0;
                row++;
            }
        }
        return intArr;
    }
    /**
     * Convert list of arrays to list of integers.
     * @param list - list of arrays
     * @return list of integers
     */
    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] arr : list) {
            for (int val : arr) {
                result.add(val);
            }
        }
        return result;
    }
}
