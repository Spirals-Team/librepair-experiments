package ru.curriculum.domain.admin.user.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Target;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.service.user.dto.UserDTO;

import javax.persistence.*;

@Entity
@Table(name = "users")
@EqualsAndHashCode(of = { "id", "username" })
@Getter
@Accessors(fluent = true)
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @Embedded
    @AttributeOverride(column = @Column(name = "password"), name = "password")
    @Target(Password.class)
    private Password password;
    private String firstName;
    private String surname;
    private String patronymic;

    // TODO: Либо много ролей,
    // TODO: либо роль определяетмя разрешениями,
    // TODO: либо ограничимя одной ролью
    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private Teacher teacher;

    public User() {
        this.firstName = "";
        this.surname = "";
        this.patronymic = "";
        this.role = new Role("user", "Пользователь");
    }

    public User(
            @NonNull String username,
            String password,
            String surname,
            String firstName,
            String patronymic
    ) {
        this();
        this.username = username;
        this.password = new Password(password);
        this.surname = null != surname ? surname : "";
        this.firstName = null != firstName ? firstName : "";
        this.patronymic = null != patronymic ? patronymic : "";
    }

    public User(UserDTO userDTO) {
        this(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getSurname(),
                userDTO.getFirstName(),
                userDTO.getPatronymic());
    }

    public void assignRole(Role role) {
        this.role = role;
    }

    public void updatePrincipal(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.patronymic = userDTO.getPatronymic();
        this.surname = userDTO.getSurname();
        if(userDTO.passwordIsPresent()) {
            this.password = new Password(userDTO.getPassword());
        }
    }

    public String fullName() {
        String firstNameShort = !firstName.isEmpty() ?
                firstName.substring(0, 1).toUpperCase().concat(".") : "";
        String patronymicShort = !patronymic.isEmpty() ?
                patronymic.substring(0, 1).toUpperCase().concat(".") : "";

        return surname.concat(" ").concat(firstNameShort).concat(patronymicShort);
    }

    public void changePassword(String password) {
        this.password = new Password(password);
    }

    public boolean isTeacher() {
        return null != teacher;
    }
}
