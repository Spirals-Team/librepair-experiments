package ru.job4j.max;

/**
 * Max class.
 *
 * * @author Artem Lipatov
 * */

public class Max {

    /**
     * method for finding max number.
     *
     * * @author Artem Lipatov
     * * @param first 1 number
     * * @param second 2 number
     * * @return larger number
     */

    public int max(int first, int second) {
        return (first > second) ? first : second;
    }

    public int max(int first, int second, int third) {
        int rsl = max(first, second);
        rsl = max(rsl, third);
        return rsl;
    }
}
