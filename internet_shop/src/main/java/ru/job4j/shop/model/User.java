package ru.job4j.shop.model;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 13.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class User {
//•	ID
//•	Имя (логин)
//•	Пароль
//•	Роль (пользователь или администратор)

    private int id;
    private String name;
    private String password;
    private String role;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{"
                + "id='"
                + id
                + '\''
                + ", name='"
                + name
                + '\''
                + ", password='"
                + password
                + '\''
                + ", role='"
                + role
                + '\''
                + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
