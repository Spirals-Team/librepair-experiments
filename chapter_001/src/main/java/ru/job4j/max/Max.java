package ru.job4j.max;

/**
 * @author Yury Matskevich(y.n.matskevich@gmail.com)
 * @version $Id$
 * @since 23.12.2017
 */
public class Max {
    public int max(int first, int second) {
        return first > second ? first : second;
    }
    public int max(int first, int second, int third) {
        return max(max(first, second), third);
    }
}