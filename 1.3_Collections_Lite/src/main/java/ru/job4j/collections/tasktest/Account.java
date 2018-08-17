package ru.job4j.collections.tasktest;

/**
 * Class Account.
 */
public class Account {
    /**
     * @param value money
     */
    private double value;
    /**
     * @param requisites account number
     */
    private String requisites;

    /**
     * Constructor.
     * @param value money
     * @param requisites account number
     */
    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    /**
     * Getter Value.
     * @return value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Setter value.
     * @param value new value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Getter Requisites.
     * @return requisites
     */
    public String getRequisites() {
        return requisites;
    }
}
