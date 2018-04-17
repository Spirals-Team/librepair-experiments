package ru.job4j.array;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Matrix {
    int[][] multiple(int size) {
        int[][] array = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = i * j;
            }
        }
        return array;
    }
}
