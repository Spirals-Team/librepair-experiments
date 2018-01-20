package ru.curriculum.domain.admin.user.entity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.curriculum.domain.admin.user.exceptions.IllegalPassword;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@EqualsAndHashCode(of = {"hashedPassword"})
public class Password {
    @Transient
    private PasswordEncoder encoder;
    @Column(name = "password")
    private String hashedPassword;

    public Password() {
    }

    public Password(@NonNull String password) {
        if(3 > password.length()) {
            throw new IllegalPassword();
        }
        this.encoder = new BCryptPasswordEncoder(11);
        this.hashedPassword = encoder.encode(password);
    }

    public String hash() {
        return hashedPassword;
    }
}
