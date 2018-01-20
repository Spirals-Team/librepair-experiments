package ru.curriculum.domain.teacher.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import ru.curriculum.domain.admin.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teacher")
@EqualsAndHashCode(of = {"id"})
@Getter
@Accessors(fluent = true)
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String surname;
    private String firstName;
    private String patronymic;
    @OneToOne(fetch = FetchType.EAGER)
    private AcademicDegree academicDegree;
    private String placeOfWork;
    private String position;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Teacher() {
        this.firstName = "";
        this.surname = "";
        this.patronymic = "";
        this.placeOfWork = "ГАОУ ДПО Институт Развития Образования РТ";
    }

    public Teacher(
            Integer id,
            @NonNull String surname,
            @NonNull String firstName,
            @NotNull String patronymic,
            @NonNull AcademicDegree academicDegree,
            String placeOfWork,
            String position
    ) {
        this();
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.academicDegree = academicDegree;
        if(null != placeOfWork) {
            this.placeOfWork = placeOfWork;
        }
        this.position = position;
    }

    public String fullName() {
        String firstNameShort = !firstName.isEmpty() ?
                firstName.substring(0, 1).toUpperCase().concat(".") : "";
        String patronymicShort = !patronymic.isEmpty() ?
                patronymic.substring(0, 1).toUpperCase().concat(".") : "";

        return surname.concat(" ").concat(firstNameShort).concat(patronymicShort);
    }

    public User userAccount() {
        return user;
    }

    public void assignUserAccount(User user) {
        this.user = user;
    }

    public boolean hasUserAccount() {
        return null != user;
    }

    public void deleteUserAccount() {
        this.user = null;
    }
}
