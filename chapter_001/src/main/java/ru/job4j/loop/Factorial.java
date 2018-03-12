package ru.job4j.loop;

/**
 * Class Factorial.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 13.09.2017
 */
public class Factorial {
    /**
     * Method calc. Принимает на вход 1 параметр на выходе вернет факториал заданного числа.
     *
     * @param n число
     * @return Факториал
     */
    public int calc(int n) {
        int resault = 1;
        for (int i = 1; i <= n; i++) {
            resault = resault * i;
        }
        return resault;
    }
}
