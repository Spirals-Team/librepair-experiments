package io.recruiter.application.common.database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    @Id
    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String firstname;

    @Setter
    @Getter
    private String lastname;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private Boolean enabled;

    @Setter
    @Getter
    private Date lastPasswordResetDate;

    @Setter
    @Getter
    private List<AuthorityName> authorities;

    public User() {
        this.enabled = Boolean.TRUE;
        this.authorities = new ArrayList<>();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
