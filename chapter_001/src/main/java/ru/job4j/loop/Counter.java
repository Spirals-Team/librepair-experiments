package ru.job4j.loop;
/**
 * Counter.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Counter {
    /**
     * Counts the sum of even numbers.
     * @param start - start value
     * @param finish - finish value
     * @return sum of even numbers
     */
    public int add(int start, int finish) {
        int result = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                result += i;
            }
        }
        return result;
    }
}
