package ru.curriculum.domain.admin.domain.admin;

import boot.IntegrationBoot;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.curriculum.domain.admin.user.entity.Role;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.helper.UserTestFactory;
import ru.curriculum.service.user.dto.UserDTO;

import static org.junit.Assert.*;

public class UserTest extends IntegrationBoot {

    private PasswordEncoder encoder;

    @Autowired
    private UserTestFactory userTestFactory;

    @Before
    public void setUp() {
        encoder = new BCryptPasswordEncoder(11);
    }

    @Test
    public void createUser() {
        User user = userTestFactory.createTestUser();

        assertEquals("test", user.username());
        assertTrue(encoder.matches("123", user.password().hash()));
        assertEquals("Иванов", user.surname());
        assertEquals("Иван", user.firstName());
        assertEquals("Иванович", user.patronymic());
        assertEquals("Иванов И.И.", user.fullName());
    }

    @Test
    public void createUserFromUserDTO() {
        UserDTO dto = getUserDTO();
        User user = new User(dto);

        assertNull("Id is not created because only system generate id", user.id());
        assertEquals(user.username(), dto.getUsername());
        assertTrue(encoder.matches("3333", user.password().hash()));
        assertEquals(user.firstName(), dto.getFirstName());
        assertEquals(user.surname(), dto.getSurname());
        assertEquals(user.patronymic(), dto.getPatronymic());
    }

    @Test
    public void updateUserPrincipalInfo() {
        UserDTO dto = getUserDTO();
        User user = userTestFactory.createTestUser();

        user.updatePrincipal(dto);

        assertNotEquals("Username immutable", user.username(), dto.getUsername());
        assertEquals(user.firstName(), dto.getFirstName());
        assertEquals(user.surname(), dto.getSurname());
        assertEquals(user.patronymic(), dto.getPatronymic());
        assertTrue(encoder.matches(dto.getPassword(), user.password().hash()));
    }

    @Test
    public void updateUserPrincipalInfoFromDTOWhereNoPassword_passwordRemainsTheSame() {
        UserDTO dto = getUserDTO();
        dto.setPassword(null);
        User user = userTestFactory.createTestUser();

        user.updatePrincipal(dto);

        assertTrue(encoder.matches("123", user.password().hash()));
    }

    @Test
    public void updateUserPrincipalInfoFromDTOWherePasswordIsEmtpyString_passwordRemainsTheSame() {
        UserDTO dto = getUserDTO();
        dto.setPassword("");
        User user = userTestFactory.createTestUser();

        user.updatePrincipal(dto);

        assertTrue(encoder.matches("123", user.password().hash()));
    }

    @Test
    public void createRole() {
        Role role = new Role("test", "Тестовая роль");

        assertEquals("test", role.code());
        assertEquals("Тестовая роль", role.name());
    }

    @Test
    public void assignRoleForUser() {
        Role testRole = new Role("test", "Тестовая роль");
        User user = userTestFactory.createTestUser();
        user.assignRole(testRole);

        assertEquals(testRole, user.role());
    }

    @Test
    public void getFullNameWhenPartsOfFullNameIsEmpty_mustReturnCorrect() {
        User user = new User();

        assertEquals(" ", user.fullName());
    }

    private UserDTO getUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(22);
        dto.setUsername("newUserName");
        dto.setPassword("3333");
        dto.setFirstName("newName");
        dto.setSurname("newSurname");
        dto.setPatronymic("newLastName");

        return dto;
    }



    //TODO: updateUserPrincipal and changePassword
}