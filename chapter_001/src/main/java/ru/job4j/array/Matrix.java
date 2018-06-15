package ru.job4j.array;

public class Matrix {
    int[][] multiple(int size) {
        int[][] matrix = new int[size][size];
        for (int in = 0; in < size; in++) {
            for (int out = 0; out < size; out++) {
                matrix[in][out] = (in + 1) * (out + 1);
            }
        }
        return matrix;
    }
}
