package ru.job4j.bank;

/**
 * Класс, определяющий клиента банка
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class User {
    // имя клиента
    private String name;
    // документ клиента
    private String passport;

    // конструктор по умолчани
    public User() {
    }

    // конструктор с параметрами
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    // геты
    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    // переписываем сравнение.. сравниваем по обоим параметрам
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return passport != null ? passport.equals(user.passport) : user.passport == null;
    }

    // переписываем расчет хешкода
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        return result;
    }
}
