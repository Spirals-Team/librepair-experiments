package ru.job4j.array;

public class RotateArray {
    public int[][] rotate(int[][] array) {
        int n = array.length;
        int[][] newArray = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                newArray[i][j] = array[n - j - 1][i];
            }
        }
        return newArray;
    }
}
