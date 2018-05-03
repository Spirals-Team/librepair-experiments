package ru.job4j.calculator;
/**@autor Alexander Kaleganov
 * @version 1.001
 * @since 26.01.2018 22:25
 */
public class Calculator {
    private double result;

    public void add(double first, double second) {
        this.result = first + second;
    }
    public void substract(double first, double second) {
        this.result = first - second;
    }
    public void div(double first, double second) {
        this.result = first / second;
    }
    public void multiple(double first, double second) {
        this.result = first * second;
    }

    public double getResult() {
        return this.result;
    }

}
