package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Matrix {

    /**
     * Метод создания матрицы таблицы умножения размерность size.
     * @param size - размер матрицы.
     * @return матрица умножения.
     */
    public int[][] multiple(int size) {
        int[][] newArray = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newArray[i][j] = (i + 1) * (j + 1);
            }
        }
        return newArray;
    };
}
