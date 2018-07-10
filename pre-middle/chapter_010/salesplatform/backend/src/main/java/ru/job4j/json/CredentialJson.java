package ru.job4j.json;

import java.util.Objects;
import java.util.StringJoiner;

public class CredentialJson {

    private String email;
    private String password;

    public CredentialJson() {
    }

    public CredentialJson(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public CredentialJson setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CredentialJson setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CredentialJson that = (CredentialJson) o;
        return Objects.equals(this.email, that.email)
            && Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("email = " + email)
            .add("password = " + password)
            .toString();
    }
}
