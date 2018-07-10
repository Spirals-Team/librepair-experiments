package ru.job4j.json;

import java.util.Objects;
import java.util.StringJoiner;

public class AuthJson {

    private String idToken;
    private Long localId;
    private Long expiresIn;

    public AuthJson() {
    }

    public AuthJson(final String idToken, final Long localId,
                    final Long expiresIn) {
        this.idToken = idToken;
        this.localId = localId;
        this.expiresIn = expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public AuthJson setIdToken(final String idToken) {
        this.idToken = idToken;
        return this;
    }

    public Long getLocalId() {
        return localId;
    }

    public AuthJson setLocalId(final Long localId) {
        this.localId = localId;
        return this;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public AuthJson setExpiresIn(final Long expiresIn) {
        this.expiresIn = expiresIn;
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
        AuthJson that = (AuthJson) o;
        return Objects.equals(this.idToken, that.idToken)
            && Objects.equals(this.localId, that.localId)
            && Objects.equals(this.expiresIn, that.expiresIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idToken, localId, expiresIn);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("idToken = " + idToken)
            .add("localId = " + localId)
            .add("expiresIn = " + expiresIn)
            .toString();
    }
}
