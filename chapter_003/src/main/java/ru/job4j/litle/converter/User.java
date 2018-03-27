package ru.job4j.litle.converter;
/**
 * User.
 * @author Hincu Andrei (andreih1981@gmail.com) by 17.09.17;
 * @version $Id$
 * @since 0.1
 */
public class User {
    /**
     * id.
     */
    private int id;
    /**
     * Имя.
     */
    private String name;
    /**
     * Город.
     */
    private String city;

    /**
     * Конструктор.
     * @param id ид.
     * @param name имя.
     * @param city город.
     */
    public User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    /**
     * Equals.
     * @param o сраниваемый обьект.
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

        if (id != user.id) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return city != null ? city.equals(user.city) : user.city == null;
    }

    /**
     * HashCode.
     * @return code.
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    /**
     * getter id.
     * @return id.
     */
    public int getId() {
        return id;
    }
}
