package ru.job4j.calculator;
/**
 * Calculator.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Calculator {
    /**
     * Результат вычислений.
     */
    private double result;
    /**
     * Method add сумма значений.
     * @param first первое значение.
     * @param second второе значение.
     */
    public void add(double first, double second) {
        this.result =  first + second;
    }
    /**
     * Method sub разность значений.
     * @param first первое значение.
     * @param second второе значение.
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }
    /**
     * Method div.
     * @param first первое значение.
     * @param second второе значение.
     */
    public void div(double first, double second) {
        this.result = first / second;
    }
    /**
     * Method multiple.
     * @param first первое значение.
     * @param second второе значение.
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }
    /**
     * Method getResult.
     * @return result.
     */
    public double getResult() {
        return result;
    }
}
