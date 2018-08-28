package ru.job4j.array;

/**
 * Class Turn. Переворчиваем массив.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 14.09.2017
 */
public class Turn {
    /**
     * Method back. Принимает на вход массив на выходе вернет массив наоборот.
     *
     * @param array массив
     * @return Переернутый массив
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
