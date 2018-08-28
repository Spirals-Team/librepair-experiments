package ru.job4j.array;
/**
 * Class BubbleSort. Сортируем массив методом Bubble sort.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 14.09.2017
 */
public class BubbleSort {
    /**
     * Method sort. Принимает на вход массив на выходе вернет сортированыый массив.
     *
     * @param array массив
     * @return Сортированыый массив
     */
    public int[] sort(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        return array;
    }
}
