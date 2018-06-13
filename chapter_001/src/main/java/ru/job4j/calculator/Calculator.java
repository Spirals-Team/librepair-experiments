package ru.job4j.calculator;

public class Calculator {
    /**
     * Метод складывает аргумент first и second и записывает результат в поле this.result.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    private double result;

    public void add(double first, double second) {
        this.result = first + second;
    }

    public void subtract(double first, double second) {
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