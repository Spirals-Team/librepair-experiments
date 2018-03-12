package ru.job4j;

/**
 * Class Calculator.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 08.09.2017
 */
public class Calculator {
    /**
     * Переменная класса.
     */
    private double result;

    /**
     * Method add.
     *
     * @param fist первое число
     * @param second второе число
     */
    public void add(double fist, double second) {
        this.result = fist + second;
    }

    /**
     * Method subtract. Делаем вычетание.
     *
     * @param fist первое число
     * @param secsecond второе число
     */
    public void subtract(double fist, double secsecond) {
        this.result = fist - secsecond;
    }

    /**
     * Method div. Делаем деление.
     *
     * @param fist первое число
     * @param secsecond второе число
     */
    public void div(double fist, double secsecond) {
        this.result = fist / secsecond;
    }

    /**
     * Method multiple. Делаем умножение.
     *
     * @param fist первое число
     * @param secsecond второе число
     */
    public void multiple(double fist, double secsecond) {
        this.result = fist * secsecond;
    }

    /**
     * Method getResult. Возвращаем результат.
     *
     * @return this.result
     */
    public double getResult() {
        return this.result;
    }
}
