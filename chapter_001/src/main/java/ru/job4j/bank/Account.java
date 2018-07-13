package ru.job4j.bank;

/**
 * Класс, определяющий счет в банке
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class Account {

    // реквизиты счета
    private String requisites;
    // сумма на счете
    private double value;

    // конструктор по умолчанию
    public Account(){
    }

    // конструктор с параметрами
    public Account(String requisites, double value) {
        this.requisites = requisites;
        this.value = value;
    }

    // геты/ сеты

    public double getValue() {
        return value;
    }

    public String getRequisites() {
        return requisites;
    }

    public void setValue(double value) {
        this.value = value;
    }

    // переписываем сравнение.. сравниваем только по реквизитам
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return requisites.equals(account.requisites);
    }

    // переписываем хешкод
    @Override
    public int hashCode() {
        return requisites.hashCode();
    }
}
