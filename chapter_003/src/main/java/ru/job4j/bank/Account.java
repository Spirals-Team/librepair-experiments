package ru.job4j.bank;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Account {
    private double value;
    private String requisites;

    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public void changeBalance(double value) {
        this.value += value;
    }

    public double getValue() {
        return value;

    }

    public String getRequisites() {
        return requisites;
    }
}
