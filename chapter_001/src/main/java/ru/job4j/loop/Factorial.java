package ru.job4j.loop;

/**
 * Factorial.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Factorial {
    /**
     * Calculates the factorial of number n.
     * @param n - number for factorial
     * @return factorial number n
     */
    public int calc(int n) {
        int result = 1;
        if (n == 0) {
            return result;
        }
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
