package ru.job4j.calculator;

/**
 *class Calculator
 *@author Yury Matskevich
 *@version $Id$
 *@since 19.12.2017
 */

public class Calculator {
    private double resultAdd;
    private double resultSub;
    private double resultDiv;
    private double resultMul;

    public void add(double first, double second) {
        this.resultAdd = first + second;
    }

    public double getResultAdd() {
        return this.resultAdd;
    }

    public void sub(double first, double second) {
        this.resultSub = first - second;
    }

    public double getResultSub() {
        return this.resultSub;
    }

    public void div(double first, double second) {
        this.resultDiv = first / second;
    }

    public double getResultDiv() {
        return this.resultDiv;
    }

    public void mul(double first, double second) {
        this.resultMul = first * second;
    }

    public double getResultMul() {
        return this.resultMul;
    }
}