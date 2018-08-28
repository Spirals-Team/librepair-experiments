package ru.job4j.array;

/**
 * Class CheckSortedArray. Проверяем сортирован массив или нет.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 24.09.2017
 */
public class CheckSortedArray {
    /**
     * Method checkSortedArray. Принимает на вход массив на выходе вернет если массив сортирован (true).
     *
     * @param array массив
     * @return boolean
     */
    public boolean checkSortedArray(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
