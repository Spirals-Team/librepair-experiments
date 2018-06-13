package ru.job4j.loop;

public class Factorial {
    /**
     * Метод calc вычисляет факториал.
     *
     * @param factorial - факторил числа.
     * @return - возвращает факторил.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int calc(int factorial) {
        int result = 1;
        for (int number = 1; number <= factorial; number++) {
            result = result * number;
        }
        return result;
    }
}
