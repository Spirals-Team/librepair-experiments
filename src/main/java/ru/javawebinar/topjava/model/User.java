package ru.javawebinar.topjava.model;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/** @author danis.tazeev@gmail.com */
@Entity
@Table(name = "\"user\"")
//@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends IdentifiedEntity {
    @NotBlank
    @Column(name = "name")
    private String name;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private final byte[] password = new byte[16]; // MD5
    @Column(name = "admin")
    private boolean admin;
    @Column(name = "enabled")
    private boolean enabled = true;
    @Min(0)
    @Column(name = "calories_per_day_limit")
    private int caloriesPerDayLimit;
    @Convert(converter = JavaEpochMillisTimestampConverter.class)
    @Column(name = "registered_at")
    private final long registeredAt;
/*
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
*/
    public static byte[] password(String password) {
        try {
            return MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException neverHappens) {
            throw new AssertionError("just to get compiled");
        }
    }

    public User() {
        registeredAt = System.currentTimeMillis();
    }

    public User(String name, String email, byte[] password, int caloriesPerDayLimit) {
        this(name, email, password, caloriesPerDayLimit, System.currentTimeMillis());
    }

    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    public User(String name, String email, byte[] password, int caloriesPerDayLimit, long registeredAt) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setCaloriesPerDayLimit(caloriesPerDayLimit);
        this.registeredAt = registeredAt;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isEnabled() { return enabled; }
    public boolean isAdmin() { return admin; }
    public int getCaloriesPerDayLimit() { return caloriesPerDayLimit; }
    public long getRegisteredAt() { return registeredAt; }
    public byte[] getPassword() {
        byte[] password = new byte[this.password.length];
        System.arraycopy(this.password, 0, password, 0, password.length);
        return password;
    }

    @Override
    public User setId(Integer id) {
        return (User)super.setId(id);
    }

    public User setName(String name) {
        Objects.requireNonNull(name, "name");
        this.name = name;
        return this;
    }

    /** @throws NullPointerException if... */
    public User setEmail(String email) {
        Objects.requireNonNull(email, "email");
        this.email = email;
        return this;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * @throws IllegalArgumentException if... */
    public User setCaloriesPerDayLimit(int caloriesPerDayLimit) {
        Preconditions.checkArgument(caloriesPerDayLimit >= 0, "caloriesPerDayLimit=%s", caloriesPerDayLimit);
        this.caloriesPerDayLimit = caloriesPerDayLimit;
        return this;
    }

    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    public User setPassword(byte[] password) {
        Objects.requireNonNull(password, "password");
        Preconditions.checkArgument(this.password.length == password.length,
                "password.length MUST be %s (MD5)", this.password.length);
        System.arraycopy(password, 0, this.password, 0, password.length);
        return this;
    }

    @Override
    public String toString() {
        return "U#" + getId()
                + ':' + name + '/' + email
                + '×' + caloriesPerDayLimit
                + ';' + (enabled ? "enabled" : "disabled");
    }
}
