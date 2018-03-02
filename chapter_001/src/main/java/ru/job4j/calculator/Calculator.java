package ru.job4j.calculator;

/**
 *  Calculatot.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Calculator {
    /**
     * result of calculation.
     */
    private double result;
    /**
     * getResult.
     * @return result
     */
    public double getResult() {
        return this.result;
    }
    /**
     * add.
     * @param first - first value.
     * @param second - second value.
     */
    public void add(double first, double second) {
        this.result = first + second;
    }
    /**
     * subtract.
     * @param first - first value.
     * @param second - second value.
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }
    /**
     * div.
     * @param first - first value.
     * @param second - second value.
     */
    public void div(double first, double second) {
        this.result = first / second;
    }
    /**
     * multiple.
     * @param first - first value.
     * @param second - second value.
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }
}
