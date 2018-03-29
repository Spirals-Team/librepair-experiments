package ru.job4j.calculator;

/**
 * Calculator class.
 *
 * * @author Artem Lipatov
 * */

public class Calculator {

    /**
     *
     * * @author Artem Lipatov
     * */

    private double result;

    /**
     * method for adding two double numbers.
     *
     * * @author Artem Lipatov
     * * @param first 1 number
     * * @param second 2 number
     * * @version 1.0
     */

    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * method for substructing two double numbers.
     *
     * * @author Artem Lipatov
     * * @param first 1 number
     * * @param second 2 number
     * * @version 1.0
     * */

    public void sub(double first, double second) {
        this.result = first - second;
    }

    /**
     * method for multiplying two double numbers.
     *
     * * @author Artem Lipatov
     * * @param first first number
     * * @param second second number
     * */

    public void mul(double first, double second) {
        this.result = first * second;
    }

    /**
     * method for dividing two double numbers.
     *
     * * @author Artem Lipatov
     * * @param first 1 number
     * * @param second 2 number
     * */

    public void div(double first, double second) {
        this.result = first / second;
    }

    /**
     * result getter.
     *
     * * @author Artem Lipatov
     * * @return result
     * */

    public double getResult() {
        return this.result;
    }
}
