package ru.job4j.max;

/**
 * Get max value.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 06.10.2017
 */
public class Max {

    /**
     * @class verible result Result value.
     */
    private int result;

    /**
     * Method max.
     * @param first First value.
     * @param second Second value.
     * @return max value
     */
    public int max(int first, int second) {
        this.result = Math.max(first, second);

        return this.result;
    }

    /**
     * Method max.
     * @param first First value.
     * @param second Second value.
     * @param third Third value.
     * @return max value
     */
    public int max(int first, int second, int third) {
//        return 1;
        return max(max(first, second), third);
    }

}