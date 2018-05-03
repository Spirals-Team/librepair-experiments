package ru.job4j.array;

/**
 * @author Alexander Kaleganov
 * @version 10.11
 * @since 03.02.2018
 * программа сравнивает элементы массива
 * и если элемент массива  равен входящему значению,
 * то возвращает индекс элемента
 */
public class FindLoop {
    public int indexOf(int[]armass, int el) {
        int rsl = -1;
        for (int i = 0; i < armass.length; i++) {
            if (armass[i] == el) {
                rsl = i;
                break;
            }
        } return rsl;
    }
}
