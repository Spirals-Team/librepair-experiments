package ru.curriculum.domain.admin.domain.teacher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.domain.teacher.entity.AcademicDegree;
import ru.curriculum.domain.teacher.repository.AcademicDegreeRepository;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.service.teacher.dto.TeacherDTO;
import ru.curriculum.domain.teacher.factory.TeacherFactory;

import javax.persistence.EntityNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TeacherFactoryTest extends Assert {
    @Mock
    private AcademicDegreeRepository academicDegreeRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TeacherFactory teacherFactory;

    @Before
    public void setUp() {
        Mockito
                .when(academicDegreeRepository.findOne("ph_d"))
                .thenReturn(new AcademicDegree("ph_d", "Доктор наук"));
        Mockito
                .when(academicDegreeRepository.findOne("none"))
                .thenReturn(null);
        Mockito
                .when(userRepository.findOne(1))
                .thenReturn(new User());
    }

    @Test
    public void createTeacher() {
        TeacherDTO dto = getTeacherDTO();
        Teacher teacher = teacherFactory.create(dto);

        assertEquals(dto.getSurname(), teacher.surname());
        assertEquals(dto.getFirstName(), teacher.firstName());
        assertEquals(dto.getPatronymic(), teacher.patronymic());
        assertEquals(dto.getAcademicDegreeCode(), teacher.academicDegree().code());
        assertEquals(dto.getAcademicDegreeName(), teacher.academicDegree().name());
        assertEquals(dto.getPlaceOfWork(), teacher.placeOfWork());
        assertEquals(dto.getPosition(), teacher.position());
    }

    @Test(expected = EntityNotFoundException.class)
    public void createTeacherFromDTOWhichContainNoneExistenceAcademicDegreeCode_mustBeException() {
        TeacherDTO dto = getTeacherDTO();
        dto.setAcademicDegreeCode("none");
        teacherFactory.create(dto);
    }

    @Test(expected = NullPointerException.class)
    public void createTeacherFromDTOWhichNotContainSurname_mustBeException() {
        TeacherDTO dto = getTeacherDTO();
        dto.setSurname(null);
        teacherFactory.create(dto);
    }

    @Test
    public void createUserFromUserDTOWhereDefineUserId_mustBeCreateTeacherWithUserAccount() {
        TeacherDTO dto = getTeacherDTO();
        dto.setUserId(1);
        Teacher teacher = teacherFactory.create(dto);

        assertTrue(teacher.hasUserAccount());
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
