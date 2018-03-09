package ru.aserdyuchenko.crudServlet;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class User {
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    private String email;
    private String createDate;

    public User(String name, String email, String createDate) {
        this.name = name;
        this.email = email;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
