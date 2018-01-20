package ru.curriculum.domain.admin.service.teacher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.curriculum.service.teacher.dto.TeacherDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class TeacherDTOValidationTest extends Assert {
    private Validator validator;
    private ValidatorFactory validatorFactory;

    @Before
    public void setUp() {
        validatorFactory= Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void teacherDTOWithAllValidField_validationSuccess() {
        TeacherDTO dto = getTeacherDTO();

        Set<ConstraintViolation<TeacherDTO>> violation = validator.validate(dto);

        assertEquals(0, violation.size());
    }

    @Test
    public void teacherDTOWithEmptyFullName_validationFailed() {
        TeacherDTO dto = getTeacherDTO();
        dto.setSurname(null);
        dto.setFirstName("");
        dto.setPatronymic("");

        Set<ConstraintViolation<TeacherDTO>> violation = validator.validate(dto);

        assertEquals(3, violation.size());
    }

    @Test
    public void teacherDTOWithEmptyAcademicDegreeCode_validationFailed() {
        TeacherDTO dto = getTeacherDTO();
        dto.setAcademicDegreeCode(null);

        Set<ConstraintViolation<TeacherDTO>> violation = validator.validate(dto);

        assertEquals(1, violation.size());
    }

    public TeacherDTO getTeacherDTO() {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(1);
        teacherDTO.setSurname("Иванов");
        teacherDTO.setFirstName("Иван");
        teacherDTO.setPatronymic("Иванович");
        teacherDTO.setAcademicDegreeCode("ph_d");
        teacherDTO.setAcademicDegreeName("Доктор наук");
        teacherDTO.setPlaceOfWork("ИРОРТ");
        teacherDTO.setPosition("Преподователь информатики");

        return teacherDTO;
    }
}
