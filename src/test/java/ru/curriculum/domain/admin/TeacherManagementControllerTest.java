package ru.curriculum.domain.admin;

import boot.IntegrationWebBoot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.domain.teacher.entity.AcademicDegree;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.domain.teacher.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.curriculum.domain.teacher.entity.AcademicDegreeCode.*;

@WithMockUser
public class TeacherManagementControllerTest extends IntegrationWebBoot {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    private List<Teacher> teachers;
    private User user;

    @Before
    public void setUp() {
        super.setUp();
        user = createUser();
        teachers = createTeachers();
    }

    @After
    public void tearDown() {
        teacherRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getTeacherForm() throws Exception {
        mockMvc.perform(get("/admin/teachers/new"))
                .andExpect(view().name("admin/teachers/teacherForm"));
    }

    @Test
    public void createTeacher() throws Exception {
        mockMvc.perform(post("/admin/teachers")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("patronymic", "test")
                .param("firstName", "test")
                .param("surname", "test")
                .param("academicDegreeCode", PH_D))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/teachers"))
                .andDo(print());
    }

    @Test
    public void editTeacher() throws Exception {
        mockMvc.perform(post("/admin/teachers")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", teachers.get(0).id().toString())
                .param("patronymic", teachers.get(0).patronymic())
                .param("firstName", teachers.get(0).firstName())
                .param("surname", teachers.get(0).surname())
                .param("academicDegreeCode", PROFESSOR))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/teachers"))
                .andDo(print());
        Teacher teacher = teacherRepository.findOne(teachers.get(0).id());

        assertEquals(PROFESSOR, teacher.academicDegree().code());
    }

    @Test
    public void deleteTeacher() throws Exception {
        String url = "/admin/teachers/delete/" + teachers.get(1).id();
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/teachers"))
                .andDo(print());

        Teacher teacher = teacherRepository.findOne(teachers.get(1).id());

        assertNull("Teacher deleted", teacher);
    }

    @Test
    public void getNewTeacherFromUserForm() throws Exception {
        mockMvc.perform(get("/admin/teachers/newFromUser/{id}", user.id())
                .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("admin/teachers/teacherForm"))
                .andExpect(model().attributeExists("teacher"))
                .andExpect(model().attributeExists("academicDegrees"))
                .andExpect(model().attributeExists("userAccounts"));
    }

    @Test
    public void createTeacherFromUserAccount_mustBeCreateUserWithAccount() throws Exception {
        mockMvc.perform(post("/admin/teachers")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", teachers.get(0).id().toString())
                .param("patronymic", teachers.get(0).patronymic())
                .param("firstName", teachers.get(0).firstName())
                .param("surname", teachers.get(0).surname())
                .param("academicDegreeCode", PROFESSOR)
                .param("userId", user.id().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/teachers"))
                .andDo(print());
        Teacher teacher = teacherRepository.findOne(teachers.get(0).id());

        assertEquals(user, teacher.userAccount());
    }

    private User createUser() {
        User user = new User(
                "xui",
                "123",
                "Иванов",
                "Иван",
                "Иванович");
        userRepository.save(user);

        return user;
    }

    private List<Teacher> createTeachers() {
        Teacher cook = new Teacher(
                null,
                "Иванов",
                "Иван",
                "Иванович",
                new AcademicDegree(PH_D, "Доктор наук"),
                "Макдоналдс",
                "Жарщик котлет");
        Teacher teacher = new Teacher(
                null,
                "Петров",
                "Петр",
                "Петрович",
                new AcademicDegree(PH_D, "Доктор наук"),
                null,
                null);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(cook);
        teachers.add(teacher);

        return (List<Teacher>) teacherRepository.save(teachers);
    }
}
