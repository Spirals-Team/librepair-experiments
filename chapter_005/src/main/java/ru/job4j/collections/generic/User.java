package ru.job4j.collections.generic;

/**
 * Класс User.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 */
public class User extends Base {
    /**
     * id элемента.
     */
    private String id;
    /**
     * Имя юсера.
     */
    private String name;

    /**
     * Конструктор класса.
     * @param id id user.
     * @param name name of user.
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Конструктор без имени.
     * @param id id.
     */
    public User(String id) {
        this.id = id;
    }

    /**
     * Getter id.
     * @return id.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Settet.
     * @param id установка id.
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equals сравнивает user по id если они одинаковые то они равны.
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

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    /**
     * hashCode.
     * @return code.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
