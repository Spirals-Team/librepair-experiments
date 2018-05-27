package ru.job4j.loop;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Factorial {

    /**
     * Метод рассчитывает факториал для числа n;
     * @param n
     * @return произвидение чисел.
     */
    public int calc(int n) {
        int sum = 1;
        if (n != 0) {
            for (int i = 1; i <= n; i++) {
                sum *= i;
            }
        }
        return sum;
    };
}
