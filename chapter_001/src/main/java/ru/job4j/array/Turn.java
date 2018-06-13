package ru.job4j.array;

public class Turn {
    /**
     * Класс переворачивает массив задом наперёд.
     * Метод back принимает в себя массив целых чисел и возвращает тот же самый массив, но перевёрнутый задом наперёд.
     *
     * @param array - массив, который переворачивают.
     * @return - возвращает массив перевёрнутый задом наперёд.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int[] back(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int x = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = x;
        }
        return array;
    }
}