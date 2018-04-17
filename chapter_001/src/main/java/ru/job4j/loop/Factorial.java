package ru.job4j.loop;

/**
 * @author Yury Matskevich(y.n.matskevich@gmail.com)
 * @version $Id$
 * @since 23.12.2017
 */
public class Factorial {
    public int calc(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}