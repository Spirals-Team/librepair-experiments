package m2dl.ivvq.sortircesoir.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1)
    private String login;

    @NotNull
    @Size(min = 1)
    @Email
    private String email;

    @NotNull
    @Size(min = 1)
    private String password;

    private String token;

    @OneToMany(mappedBy = "owner")
    private List<Place> places;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User() {
        this.places = new ArrayList<Place>();
        this.comments = new ArrayList<Comment>();
    }

    public User(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.places = new ArrayList<Place>();
        this.comments = new ArrayList<Comment>();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
