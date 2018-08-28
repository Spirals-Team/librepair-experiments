package ru.job4j;

/**
 * Class Max. Находим максимально число.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.09.2017
 */
public class Max {
    /**
     * Method max. Принимает на вход 2 параметра на выходе вернет максимально число.
     *
     * @param first  первое число
     * @param second второе число
     * @return Максимальное число
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }

    /**
     * Method max. Принимает на вход 3 параметра на выходе вернет максимально число.
     *
     * @param first  первое число
     * @param second второе число
     * @param third  третье число
     * @return Максимальное число
     */
    public int max(int first, int second, int third) {
        return max(max(first, second), third);
    }
}
