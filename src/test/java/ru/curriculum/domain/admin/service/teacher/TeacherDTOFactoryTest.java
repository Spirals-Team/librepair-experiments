package ru.curriculum.domain.admin.service.teacher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.curriculum.service.user.UserCRUDService;
import ru.curriculum.service.user.dto.UserDTO;
import ru.curriculum.service.teacher.dto.TeacherDTO;
import ru.curriculum.service.teacher.factory.TeacherDTOFactory;

@RunWith(MockitoJUnitRunner.class)
public class TeacherDTOFactoryTest extends Assert {
    @Mock
    private UserCRUDService userCRUDService;
    @InjectMocks
    private TeacherDTOFactory teacherDTOFactory;
    private UserDTO userDTO;

    @Before
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("username");
        userDTO.setSurname("Test");
        userDTO.setFirstName("Test");
        userDTO.setPatronymic("Test");
        userDTO.setPassword("123");
        Mockito
                .when(userCRUDService.getUser(1))
                .thenReturn(userDTO);
    }

    @Test
    public void createTeacherDTOBasedOnUser_mustBeCreateCorrectly() {
        TeacherDTO teacherDTO = teacherDTOFactory.createTeacherDTOBasedOnUser(1);

        assertEquals(userDTO.getFirstName(), teacherDTO.getFirstName());
        assertEquals(userDTO.getSurname(), teacherDTO.getSurname());
        assertEquals(userDTO.getPatronymic(), teacherDTO.getPatronymic());
        assertEquals(userDTO.getId(), teacherDTO.getUserId());
        assertEquals(userDTO.getUsername(), teacherDTO.getUsername());
    }
}
