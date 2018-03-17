package ru.job4j.loop;
/**
 * Factorial.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Factorial {
    /**
     * Метод вычесляет факториал числа n.
     * @param n диапазон чисел от 0 до n.
     * @return факториал n.
     */
    public int calc(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        if (n == 0) {
            result = 1;
        }
        return result;
    }
}
