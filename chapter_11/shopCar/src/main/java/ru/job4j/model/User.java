package ru.job4j.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Entity
@Table(name = "Users")
public class User extends Persistent {

    private static final long serialVersionUID = -5971624644618934547L;

    private String login;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    @Column(name = "phone", unique = true)
    private int phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Advert> adverts = new ArrayList<>();

    public User() {
        super();
    }
    public User(Long id) {
       super(id);
    }
    public User(String login, String email, String password, int phone) {
        super();
        this.login = login;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "User{"
                + "id="
                + getId()
                + ", login='"
                + login
                + '\''
                + ", email='"
                + email
                + '\''
                + ", password='"
                + password
                + '\''
                + ", phone="
                + phone
                + '}';
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return phone == user.phone
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(adverts, user.adverts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, email, password, phone, adverts);
    }
}
