package ru.curriculum.domain.admin.domain.teacher;

import org.junit.Assert;
import org.junit.Test;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.teacher.entity.AcademicDegree;
import ru.curriculum.domain.teacher.entity.Teacher;

public class TeacherTest extends Assert {

    @Test
    public void createTeacher() {
        Teacher teacher = new Teacher(
                1,
                "Иванов",
                "Иван",
                "Иванович",
                new AcademicDegree("ph_d", "Доктор наук"),
                "Макдоналдс",
                "Жарщик котлет");

        assertEquals(new Integer(1), teacher.id());
        assertEquals("Иванов", teacher.surname());
        assertEquals("Иван", teacher.firstName());
        assertEquals("Иванович", teacher.patronymic());
        assertEquals("Иванов И.И.", teacher.fullName());
        assertEquals(new AcademicDegree("ph_d", "Доктор наук"), teacher.academicDegree());
        assertEquals("Макдоналдс", teacher.placeOfWork());
        assertEquals("Жарщик котлет", teacher.position());
        assertFalse("For default teacher don't has account", teacher.hasUserAccount());
    }

    @Test
    public void createTeacherWithoutPlaceOfWork_mustBeSetDefaultPlaceOfWork() {
        Teacher teacher = new Teacher(
                1,
                "Иванов",
                "Иван",
                "Иванович",
                new AcademicDegree("ph_d", "Доктор наук"),
                null,
                "Жарщик котлет");

        assertEquals("ГАОУ ДПО Институт Развития Образования РТ", teacher.placeOfWork());
    }

    @Test
    public void assignAccountForUser() {
        Teacher teacher = new Teacher(
                1,
                "Иванов",
                "Иван",
                "Иванович",
                new AcademicDegree("ph_d", "Доктор наук"),
                "Макдоналдс",
                "Жарщик котлет");

        teacher.assignUserAccount(new User());

        assertTrue(teacher.hasUserAccount());
        assertNotNull(teacher.userAccount());
    }

    @Test
    public void getFullNameWhenPatronymicIsEmpty_mustBeCorrectFullName() {
        Teacher teacher = new Teacher(
                1,
                "Иванов",
                "Иван",
                "",
                new AcademicDegree("ph_d", "Доктор наук"),
                "Макдоналдс",
                "Жарщик котлет");

        assertEquals("Иванов И.", teacher.fullName());
    }

    // TODO: чтобы не писать разные тесты для каждого случаия null, попробовать использовать в таких случаях TestNG
    @Test(expected = NullPointerException.class)
    public void tryCreateTeacherWithoutNull_mustBeException() {
        Teacher teacher = new Teacher(
                1,
                null,
                "Иван",
                "Иванович",
                new AcademicDegree("ph_d", "Доктор наук"),
                "Макдоналдс",
                "Жарщик котлет");
    }
}
