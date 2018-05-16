package io.joaopinheiro.userservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    public User(){};

    public User(Long id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        } else if(obj instanceof User) {
            User user = (User) obj;
            return this.email.equals(user.email) &&
                    this.username.equals(user.username) &&
                    this.id.equals(user.id);
        }

        return false;
    }
}
