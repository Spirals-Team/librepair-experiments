package com.meteoradesigner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static com.meteoradesigner.model.Role.ROLE_USER;
import static java.util.Arrays.asList;

/**
 * Class implements the user entity.
 */
//TODO fix all the documentation, by using this class, -es and dots.
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name =
        "users_unique_email"), @UniqueConstraint(columnNames = {"display_name"}, name =
        "users_unique_display_name")})
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractNamedEntity {
    //Can be several equal external tasks that doesn't lead to cycle.

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank
    @Email
    @Size(min = 5, max = 6400)
    private String email;

    @Column(name = "password", nullable = false)
    @Size(max = 6433)
    private String password;

    @Column(name = "registration_time", nullable = false)
    @NotNull
    private LocalDateTime registrationTime = LocalDateTime.now();

    //TODO table with columns
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "users_roles",
                     joinColumns = @JoinColumn(
                             name = "user_id"))
    @Column(name = "role")
    //TODO lazy? HOW to do NOT EMPTY constraints during init?
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy
            = "user")
    private Set<TaskContext> contexts;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy
            = "user")
    private Set<TaskPortfolio> portfolios;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy
            = "user")
    private Set<Task> tasks;

    /**
     * Deep copying constructor.
     */
    //TODO fix
    public User(User user) {
        User userClone = SerializationUtils.clone(user);
        new User(userClone.getId(), userClone.getDisplayName(), userClone.getEmail(), userClone
                .getPassword(), userClone.getContexts(), userClone.getPortfolios(), userClone
                         .getTasks(), userClone.getRoles());
    }

    /**
     * The minimal parameters initializing constructor.
     */
    public User(Integer idToSet,
                String displayNameToSet,
                String email,
                String password,
                Role role,
                Role... roles) {
        this(idToSet, displayNameToSet, email, password, Collections.emptySet(),
             Collections.emptySet(), Collections.emptySet(), EnumSet.of(role, roles));
    }

    /**
     * The all-args constructor.
     */
    public User(Integer idToSet,
                String displayNameToSet,
                String email,
                String password,
                Set<TaskContext> contexts,
                Set<TaskPortfolio> portfolios,
                Set<Task> tasks,
                Collection<Role> roles) {
        super(idToSet, displayNameToSet);
        this.email = email;
        this.password = password;
        this.contexts = contexts;
        this.portfolios = portfolios;
        this.tasks = tasks;
        setRoles(roles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() + '\'' +
                "displayName=" + getDisplayName() + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationTime=" + registrationTime +
                ", roles=" + roles +
                '}';
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? new HashSet<>(asList(ROLE_USER)) : EnumSet
                .copyOf(roles);
    }
}