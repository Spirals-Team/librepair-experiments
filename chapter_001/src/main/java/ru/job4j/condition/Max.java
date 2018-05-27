package ru.job4j.condition;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Max {

    /**
     * Большее число из двух.
     * @param first
     * @param second
     * @return большее из двух аргументов.
     */
    public int max(int first, int second) {
        return second > first ? second : first;
    }

    /**
    * Большее из трех чисел.
    * @param first
    * @param second
    * @param third
    * @return большее из трех чисел.
    */
    public int max(int first, int second, int third) {
        return max(max(first, second), third);
    }
}