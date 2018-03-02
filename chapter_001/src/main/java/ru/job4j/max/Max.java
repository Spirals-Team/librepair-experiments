package ru.job4j.max;

/**
 * Max value.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Max {
    /**
     * Maximum of two numbers.
     * @param first - first value
     * @param second - second value
     * @return maximum of two numbers
     */
    public int max(int first, int second) {
        return (first > second) ? first : second;
    }
    /**
     * Maximum of three numbers.
     * @param first - first value
     * @param second - second value
     * @param third - third value
     * @return maximum of three numbers
     */
    public int max(int first, int second, int third) {
        return max(max(first, second), third);
    }
}
