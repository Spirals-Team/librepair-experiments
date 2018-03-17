package ru.job4j.litle.testtask;

/**
 *User .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 19.09.17;
 * @version $Id$
 * @since 0.1
 */
public class User {
    /**
     * Имя.
     */
    private String name;

    /**
     * Конструктор.
     * @param name имя.
     * @param pasport номер паспорта.
     */
    public User(String name, String pasport) {
        this.name = name;
        this.pasport = pasport;
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

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return pasport != null ? pasport.equals(user.pasport) : user.pasport == null;
    }
    /**
     * hashCode.
     * @return code.
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (pasport != null ? pasport.hashCode() : 0);
        return result;
    }



    /**
     * getter.
     * @return name.
     */
    public String getName() {

        return name;
    }

    /**
     * setter.
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter.
     * @return number.
     */
    public String getPasport() {
        return pasport;
    }

    /**
     * setter.
     * @param pasport number.
     */
    public void setPasport(String pasport) {
        this.pasport = pasport;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='"
                + name
                + '\''
                + ", pasport='"
                + pasport
                + '\''
                + '}';
    }

    /**
     * Серия паспорта.
     */
    private  String pasport;
}
