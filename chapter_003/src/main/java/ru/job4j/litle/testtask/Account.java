package ru.job4j.litle.testtask;

/**
 *Account .
 * @author Hincu Andrei (andreih1981@gmail.com) by 19.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Account {
    /**
     * колличество денег.
     */
    private double value;
    /**
     * реквизиты счета.
     */
    private int requisites;

    /**
     * конструктор.
     * @param value деньги.
     * @param requisites реквизиты.
     */
    public Account(int value, int requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    /**
     * getter.
     * @return value.
     */
    public double getValue() {
        return value;
    }

    /**
     * setter.
     * @param value value.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * геттер счета.
     * @return счет.
     */
    public int getRequisites() {
        return requisites;
    }

    /**
     * сеттер счета.
     * @param requisites реквизиты.
     */
    public void setRequisites(int requisites) {
        this.requisites = requisites;
    }

    /**
     * equals.
     * @param o o.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        return requisites == account.requisites;
    }

    /**
     * hashcode.
     * @return code.
     */
    @Override
    public int hashCode() {
        return requisites;
    }

    /**
     * добовление денег на счет.
     * @param amount сумма.
     */
    public void addToAccount(double amount) {
        this.value = this.value + amount;
    }

    /**
     * снятие денег со счета.
     * @param amount сумма.
     */
    public void transferFromAccount(double amount) {
        this.value = value - amount;
    }

    @Override
    public String toString() {
        return "Account{"
                + "value="
                + value
                + ", requisites="
                + requisites
                + '}';
    }
}
