package ru.job4j.array;

public class BubbleSort {
    /**
     * Класс служит для сортировки массива используя алгоритм сортировки пузырьком
     *
     * @param array - сортируемый массив
     * @return - возвращает отсортированный массив
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int[] sort(int[] array) {
        for (int external = array.length - 1; external > 0; external--) {
            for (int internal = 0; internal < external; internal++) {
                if (array[internal] > array[internal + 1]) {
                    int x = array[internal];
                    array[internal] = array[internal + 1];
                    array[internal + 1] = x;
                }
            }
        }
        return array;
    }
}