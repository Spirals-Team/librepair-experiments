package ru.aserdyuchenko.testTask;

/**
 * Created by anton on 05.05.17.
 */
public class Account {
    /**
     * @param value - кол-во денег.
     */
    private double value;
    /**
     * @param requisites - реквизиты счёта.
     */
    private int requisites;

    /**
     * Account - это банковский счёт.
     */
    public Account(double accountValue, int accountRequisites) {
        this.value = accountValue;
        this.requisites = accountRequisites;
    }

    @Override
    public String toString() {
        return "Account{" +
                "value=" + value +
                ", requisites=" + requisites +
                '}';
    }

    public double getValue() {
        return value;
    }

    public int getRequisites() {
        return requisites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

      //  if (Double.compare(account.value, value) != 0) return false;
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
