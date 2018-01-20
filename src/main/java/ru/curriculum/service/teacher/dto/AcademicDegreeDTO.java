package ru.curriculum.service.teacher.dto;

import lombok.Getter;
import lombok.Setter;
import ru.curriculum.domain.teacher.entity.AcademicDegree;


@Getter
@Setter
public class AcademicDegreeDTO {
    private String code;
    private String name;

    public AcademicDegreeDTO() {}

    public AcademicDegreeDTO(AcademicDegree academicDegree) {
        this.code = academicDegree.code();
        this.name = academicDegree.name();
    }
}
