package ru.job4j.review;


import java.util.Objects;

public class Account {

   private double values; //поля должны быть приватны
   private String requisites; // вместо имени переменной  regs  можно использовать более чиитаемую - requisites

    public Account(double values, String requisites) {
        this.values = values;
        this.requisites = requisites;
    }

    public double getValues() {
        return this.values;
    }


    public String getRequisites() {
        return this.requisites;
    }

    boolean transfer(Account destination, double amount) {
        boolean success = false;
        if (amount > 0 && amount < this.values && destination != null) {
            success = true;
            this.values -= amount;
            destination.values += amount;
        }
        return success;
    }

    /**
     * Метод toString
     * Можно сразу выполнить return без ввода переменной типа String.
     * @return переопределяет в читаемый вид строку представления данных по полям класса.
     */
    public String toString() {
        /*
          String otvet;
        otvet = "Account{" + "values=" + values + ", reqs='" + requisites + "\\" + "}";
        return otvet;
         */
      return "Account{" + "values=" + values + ", reqs='" + requisites + "\\" + "}";
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
        return Objects.equals(requisites, account.requisites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requisites);
    }
}