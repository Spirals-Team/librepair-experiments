package ru.job4j.array;

public class Matrix {
    /**
     * Класс осуществляет двухмерный массив. Таблицу умножения.
     *
     * @param size - указывает на размер таблицы.
     * @return - возвращает массив.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int[][] multiple(int size) {
        int[][] rst = new int[size][size];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                rst[i - 1][j - 1] = (i) * (j);
            }
        }
        return rst;
    }
}

