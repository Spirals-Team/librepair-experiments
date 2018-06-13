package ru.job4j.loop;

public class Counter {
    /**
     * Класс производит подсчет суммы чётных чисел в диапазоне.
     *
     * @param start  - начало диапазона.
     * @param finish - конец диапазона.
     * @return - возвращает суммы чётных чисел в диапазоне от start до finish.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int add(int start, int finish) {
        int result = 0;
        for (int number = start; number < finish; number++) {
            if (number % 2 != 0) {
                number++;
            }
            result += number;
        }
        return result;
    }
}