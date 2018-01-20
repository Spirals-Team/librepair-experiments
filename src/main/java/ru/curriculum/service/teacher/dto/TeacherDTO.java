package ru.curriculum.service.teacher.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import ru.curriculum.domain.teacher.entity.Teacher;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class TeacherDTO {
    private Integer id;
    @NotEmpty(message = "Необходимо заполнить поле \"Фамилия\"")
    private String surname;
    @NotEmpty(message = "Необходимо заполнить поле \"Имя\"")
    private String firstName;
    @NotEmpty(message = "Необходимо заполнить поле \"Отчество\"")
    private String patronymic;
    private String fullName;
    @NotEmpty(message = "Необходимо заполнить поле \"Академическая степень\"")
    private String academicDegreeCode;
    private String academicDegreeName;
    private String placeOfWork;
    private String position;
    private String username;
    private Integer userId;

    public TeacherDTO() {
        this.placeOfWork = "ГАОУ ДПО Институт Развития Образования РТ";
    }

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.id();
        this.surname = teacher.surname();
        this.firstName = teacher.firstName();
        this.patronymic = teacher.patronymic();
        this.fullName = teacher.fullName();
        this.academicDegreeCode = teacher.academicDegree().code();
        this.academicDegreeName = teacher.academicDegree().name();
        this.placeOfWork = teacher.placeOfWork();
        this.position = teacher.position();
        this.userId = teacher.hasUserAccount() ? teacher.userAccount().id() : null;
        this.username = teacher.hasUserAccount() ? teacher.user().username() : "";
    }
}
