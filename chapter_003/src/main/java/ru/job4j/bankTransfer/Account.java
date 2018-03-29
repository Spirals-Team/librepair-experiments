package ru.job4j.bankTransfer;

public class Account {
    private int value;
    private String requisites;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Account(String requisites) {
        this.requisites = requisites;
    }
}
