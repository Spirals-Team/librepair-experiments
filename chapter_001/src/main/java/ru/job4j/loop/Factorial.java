package ru.job4j.loop;
/**
 * calculate factorial.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 09.10.2017
 */
public class Factorial {
    /**
     * Method add.
     * @param finish ending number.
     * @return arithmetic sums of even numbers.
     */
    public int factorial(int finish) {
        int result = 1;
        if (finish == 0) {
            return result;
        } else {
            for (int i = 1; i <= finish; i++) {
                result = result * i;
            }
            return result;
        }
    }
}
