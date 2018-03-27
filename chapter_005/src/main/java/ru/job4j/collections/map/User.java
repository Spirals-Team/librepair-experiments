package ru.job4j.collections.map;

import java.util.Calendar;

/**
 * Модель User.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 15.10.17;
 * @version $Id$
 * @since 0.1
 */
public class User {
    /**
     * Имя.
     */
    private String name;
    /**
     * Колличество детей.
     */
    private int children;
    /**
     * Дата рождения.
     */
    private Calendar birthday;

    /**
     * Конструктор.
     * @param name имя.
     * @param children дети.
     */
    public User(String name, int children) {
        this.name = name;
        this.children = children;
    }

    /**
     * Расшифровка обьекта.
     * @return стринг.
     */
    @Override
    public String toString() {
        return "User{"
                + "name='"
                + name
                + '\''
                + ", children="
                + children
                + ", birthday="
                + birthday
                + '}';
    }

    /**
     *Хэш код.
     * @return число.
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + children;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    /**
     * Переопределенный метод equals.
     * @param o сравнивыемый обьект
     * @return true of false.

     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (children != user.children) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;
    }
}
