package tech.spring.structure.auth.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;
import static tech.spring.structure.auth.AuthConstants.USERNAME_MAX_LENGTH;
import static tech.spring.structure.auth.AuthConstants.USERNAME_MIN_LENGTH;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import tech.spring.structure.model.StructureEntity;

@Entity
@Table(name = "users")
public class User extends StructureEntity {

    private static final long serialVersionUID = -9129336649618128338L;

    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH)
    @NotNull(message = "Username is required!")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username can only contain alpha numeric characters!")
    @Column(nullable = false, unique = true)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotNull(message = "Password is required!")
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ElementCollection
    private List<String> oldPasswords;

    @NotNull(message = "Role is required!")
    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    @JsonIgnore
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(nullable = false)
    private final Calendar timestamp;

    @Column(nullable = false)
    private boolean enabled;

    public User() {
        super(true);
        this.oldPasswords = new ArrayList<String>();
        this.role = Role.ROLE_USER;
        this.timestamp = Calendar.getInstance();
        this.enabled = true;
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
        this.oldPasswords.add(password);
    }

    public User(String username, String password, Role role) {
        this(username, password);
        this.role = role;
    }

    public User(String username, String password, Role role, boolean enabled) {
        this(username, password, role);
        this.enabled = enabled;
    }

    public User(User user) {
        this(user.getUsername(), user.getPassword(), user.getRole(), user.isEnabled());
        this.id = user.getId();
        this.active = user.isActive();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getOldPasswords() {
        return oldPasswords;
    }

    public void setOldPasswords(List<String> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}