package ru.job4j.loop;

/**
 * @author Alexander Kaleganov
 * @version Factorial 1.00
 * @since 30/01/2018/ 15:17
 */
public class Factorial {
    public int calc(int n) {
        int temp = 1;
        for (int i = 1; i <= n; i++) {

            temp = temp * i;
        }
        return temp;
    }
}
