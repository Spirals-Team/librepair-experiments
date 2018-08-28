package ru.job4j.loop;

/**
 * Class Counter.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 13.09.2017
 */
public class Counter {
    /**
     * Method max. Принимает на вход 2 параметра на выходе вернет Сумму четных чисел.
     *
     * @param start  первое число
     * @param finish второе число
     * @return Сумма четных чисел
     */
    public int add(int start, int finish) {
        int resault = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                resault = resault + i;
            }
        }
        return resault;
    }
}
