package ru.job4j.bank;

public class Account {
    private double value;
    private String requisites;

    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public Account() {
    }

    public double getValue() {
        return value;
    }

    public String getRequisites() {
        return requisites;
    }

    /**
     * Метод проверки возможности перевода.
     * @param destination Аккаунт на которой оосвершается перевод.
     * @param amount количество денег для перевода.
     * @return возаращает перевод средств, или false.
     */
    public boolean transfer(Account destination, double amount) {
        boolean success = false;
        if (amount > 0 && destination != null && amount < this.value) {
            success = true;
            this.value -= amount;
            destination.value += amount;

        }
        return success;

    }
}
