package ru.job4j.loop;

/**
 * @author Yury Matskevich(y.n.matskevich@gmail.com)
 * @version $Id$
 * @since 23.12.2017
 */
public class Counter {
    public int add(int start, int finish) {
        int sum = 0;
        for (int i = start; i <= finish; i++) {
            sum += ((i % 2) == 0) ? i : 0;
        }
        return sum;
    }
}