package ru.job4j.array;

public class Square {
    /**
     * Класс заполняет массив степенями чисел.
     *
     * @param bound - указывает на размер массива.
     * @return - возвращает заполненный массив.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int[] calculate(int bound) {

        int[] rst = new int[bound];
        for (int x = 1; x <= bound; x++) {
            rst[x - 1] = x * x;
        }
        return rst;
    }
}