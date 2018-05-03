package ru.job4j.controlvopros;


import java.util.Objects;

public class Account {
    /**
     * поля сдела приватными
     */
    private double values;
    private String reqs;

    public Account(double values, String requisites) {
        this.values = values;
        this.reqs = requisites;
    }

    public double getValues() {
        return this.values;
    }

    public String getReqs() {  //пробел после имени метода
        return this.reqs;
    }

    /**
     * метод просто возвращает булеан значение, при этом ничего не делает с обектом, т.е. он от поля объекта
     * values отнимает amount после прибавляет к нему обратно amount не понятно зачем это делается ?
     * чтобы просто получить истину или лож?
     *
     * @param destination
     * @param amount
     * @return
     */
    boolean transfer(Account destination, double amount) {
        boolean success = false;
        if (amount > 0 && amount < this.values && destination != null) {
            success = true;
            //две строки лишние
//            this.values -= amount;
//            destination.values += amount;
        }
        return success;
    }

    /**
     * создана лишняя переменная, можно сократить и вместо закрывающей ковычки чёрточка - опечатка наверно
     *
     * @return
     */


    public String toString() {
//        String otvet;
//        otvet = "Account{" + "values=" + values + ", reqs='" + reqs + "\\" + "}";
//        return otvet;
        return "Account{" + "values=" + values + ", reqs='" + reqs + "\'" + "}";
    }

    /**
     * чтобы проверить идентичны ли оъекты, необходимо сравнивать оба его  поля
     *
     * @param o
     * @return
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

        if (Double.compare(account.values, values) != 0) {
            return false;
        }
        return reqs.equals(account.reqs);
    }

    /**
     * хеш код должен показывать идентичны объекты или нет, если ммы будем высчитывать хеш код только по одному стринг значению,
     * то хеш коды могут часто совпадать, а объекты не будут равны к примеру значение полей values  будет разное
     *
     * @return
     */
    @Override
    public int hashCode() {

        return Objects.hash(values, reqs);
    }
}