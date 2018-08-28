package ru.job4j.bank;

/**
 * Класс Account.
 */
public class Account {
    /**
     * Колличество денег на счету.
     */
    private double value;
    /**
     * Номер счета.
     */
    private int requisites;

    /**
     * Получим количество денег на счету.
     *
     * @return получим количество денег на счету.
     */
    public double getValue() {
        return value;
    }

    /**
     * Зададим количество денег на счет.
     *
     * @param value зададим колличество денег на счет.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Получим номер счета.
     *
     * @return получим номер счета.
     */
    public int getRequisites() {
        return requisites;
    }

    /**
     * Зададим номер счета.
     *
     * @param requisites зададим номер счета.
     */
    public void setRequisites(int requisites) {
        this.requisites = requisites;
    }

    /**
     * Конструктор для счета.
     *
     * @param value      количество денег.
     * @param requisites номер счета.
     */
    public Account(int value, int requisites) {

        this.value = value;
        this.requisites = requisites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        if (Double.compare(account.value, value) != 0) {
            return false;
        }
        return requisites == account.requisites;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + requisites;
        return result;
    }
}
