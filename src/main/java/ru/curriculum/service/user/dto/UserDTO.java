package ru.curriculum.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.service.user.validation.PasswordConstraint;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@PasswordConstraint
@ToString
public class UserDTO implements Serializable {
    private Integer id;
    @Size(min = 3, max = 25, message = "Длина должна быть меньше {max} и меньше {min}")
    private String username;
    private String password;
    @NotEmpty(message = "Необходимо заполнить поле \"Фамилия\"")
    private String surname;
    @NotEmpty(message = "Необходимо заполнить поле \"Имя\"")
    private String firstName;
    private String patronymic;
    private String fullName;
    private Boolean isTeacher;

    public UserDTO() {
        this.isTeacher = false;
    }

    public UserDTO(User user) {
        this.id = user.id();
        this.username = user.username();
        this.firstName = user.firstName();
        this.surname = user.surname();
        this.patronymic = user.patronymic();
        this.fullName = user.fullName();
        this.isTeacher = user.isTeacher();
    }

    public boolean passwordIsPresent() {
        return null != password && !password.isEmpty();
    }

    public String fio() {
        return String.format("%s %s %s",
                surname!=null ? surname : "",
                firstName!=null ? firstName: "",
                patronymic!=null ? patronymic: ""
        );
    }
}
