package ru.job4j.calculator;

/**
 * Calculator.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 05.10.2017
 */
public class Calculator {

    /**
     * @class verible result Result value.
     */
    private double result;

    /**
     * Method add.
     * @param first First value.
     * @param second Second value.
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * Method multiple.
     * @param first First value.
     * @param second Second value.
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }

    /**
     * Method substract.
     * @param first First value.
     * @param second Second value.
     */
    public void substract(double first, double second) {
        this.result = first - second;
    }

    /**
     * Method divide.
     * @param first First value.
     * @param second Second value.
     */
    public void divide(double first, double second) {
        if (second == 0) {
            this.result = 0;
        }
        this.result = first / second;
    }

    /**
     * Method getResult.
     *@return result
     */
    public double getResult() {
        return this.result;
    }
}