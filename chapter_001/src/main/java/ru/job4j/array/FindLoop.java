package ru.job4j.array;

public class FindLoop {
    /**
     * Класс осуществляет поиск перебором
     *
     * @param data - сортируемый массив
     * @return - возвращает индекс искомого элемента, если элемента нет в массиве, то возвращаем -1.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public int indexOf(int[] data, int el) {

        int rsl = -1;

        for (int index : data) {
            if (data[index] == el) {
                rsl = index;

                break;
            }
        }

        return rsl;
    }
}
