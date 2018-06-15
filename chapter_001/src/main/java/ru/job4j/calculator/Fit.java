package ru.job4j.calculator;

/**
 * Программа расчета идеального веса.
 */
public class Fit {
    private static final double HEIGH_MAN = 100;
    private static final double HEIGH_WOOMAN = 110;
    private static final double GROW_FACTOR = 1.15;
    /**
     * Идеальный вес ля мужчины.
     *
     * @param height рост.
     * @return идеальный вес.
     */

    double manWeight(double height) {
        return (height - HEIGH_MAN) * GROW_FACTOR;
    }
    double womanWeight(double height) {
        return (height - HEIGH_WOOMAN) * GROW_FACTOR;
    }
}