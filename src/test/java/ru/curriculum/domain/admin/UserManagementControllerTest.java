package ru.curriculum.domain.admin;

import boot.IntegrationWebBoot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
public class UserManagementControllerTest extends IntegrationWebBoot {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private List<User> users;

    @Before
    public void setUp() {
        super.setUp();
        users = createUsers();
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    private List<User> createUsers() {
        User ivan = new User(
                "ivan123",
                "123",
                "Иванов",
                "Иван",
                "Иванович");
        User petr = new User(
                "igor231",
                "231",
                "Петров",
                "Петр",
                "Петров");
        List<User> users = new ArrayList<>();
        users.add(ivan);
        users.add(petr);

        return (List<User>)userRepository.save(users);
    }

    @Test
    public void getUser() throws Exception {
        String url = "/admin/users/edit/" + users.get(0).id();
        mockMvc.perform(get(url))
                .andExpect(view().name("admin/users/userForm"))
                .andExpect(model().attributeDoesNotExist("password"))
                .andDo(print());
    }

    @Test
    public void getUserCreatingForm() throws Exception {
        mockMvc.perform(get("/admin/users/new"))
                .andExpect(view().name("admin/users/userForm"));
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(post("/admin/users")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "test")
                .param("password", "123")
                .param("firstName", "test")
                .param("surname", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"))
                .andDo(print());

        User newUser = userRepository.findByUsername("test");

        assertNotNull("User was created", newUser);
    }

    @Test
    public void editUser() throws Exception {
        mockMvc.perform(put("/admin/users")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", users.get(0).id().toString())
                .param("firstName", users.get(0).firstName())
                .param("surname", users.get(0).surname())
                .param("patronymic", "Васильевич"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"))
                .andDo(print());
        User user = userRepository.findOne(users.get(0).id());

        assertEquals("Васильевич", user.patronymic());
        assertTrue(
                "When editing user and no change password the password remains the same",
                passwordEncoder.matches( "123", user.password().hash()));
    }

    @Test
    public void changeUserPassword() throws Exception {
        mockMvc.perform(put("/admin/users")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", users.get(0).id().toString())
                .param("username", users.get(0).username())
                .param("password", "444")
                .param("firstName", users.get(0).firstName())
                .param("surname", users.get(0).surname())
                .param("patronymic", users.get(0).patronymic()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"))
                .andDo(print());
        User user = userRepository.findOne(users.get(0).id());

        assertTrue(
                "Password successfully changed",
                passwordEncoder.matches( "444", user.password().hash()));
    }


    @Test
    public void createUserWithActuallyExistUsername_mustBeOneError() throws Exception {
        // Can not be users with the same "username"
        mockMvc.perform(post("/admin/users")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", users.get(0).username())
                .param("password", "123")
                .param("firstName", "test")
                .param("surname", "test"))
                .andExpect(view().name("admin/users/userForm"))
                .andExpect(model().errorCount(1))
                .andDo(print());
    }

    @Test
    public void deleteUser() throws Exception {
        String url = "/admin/users/delete/" + users.get(1).id();
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"))
                .andDo(print());

        User user = userRepository.findOne(users.get(1).id());

        assertNull("User deleted", user);
    }
}

