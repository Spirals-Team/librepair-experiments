package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.TestUtils;
import ar.edu.undav.semillero.domain.entity.Node;
import ar.edu.undav.semillero.domain.entity.Student;
import ar.edu.undav.semillero.request.CreateStudentRequest;
import ar.edu.undav.semillero.request.FilterStudentsRequest;
import ar.edu.undav.semillero.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
@Import(SpringDataWebAutoConfiguration.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private StudentService studentService;

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(studentService);
    }

    // POST Tests
    @Test
    public void saveStudentNotPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/student"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    public void saveStudentPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void saveStudentPostParams() throws Exception {
        String name = "Juan";
        String lastName = "Last";
        long nodeId = 1001;
        LocalDate courseDate = LocalDate.of(2018, 1, 1);
        CreateStudentRequest request = new CreateStudentRequest(name, lastName, nodeId, null, courseDate, null, null, null, false, true, null);
        Student student = new Student(name, lastName, courseDate, null, new Node(), null, null, null, false, true, null);
        ReflectionTestUtils.setField(student, "id", 1L);
        Mockito.when(studentService.save(Mockito.any(CreateStudentRequest.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.post("/student").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(lastName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.interviews", Matchers.empty()));
        Mockito.verify(studentService).save(Mockito.eq(request));
    }

    @Test
    public void saveStudentPostParamsInvalid() throws Exception {
        CreateStudentRequest request = new CreateStudentRequest("First", "Last", null, null, LocalDate.of(2018, 1, 1), null, null, null, false, true, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/student").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // GET Tests

    @Test
    public void listStudents() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/student")
                    .param("node", "1")
                    .param("minGraduationDate", "2018-01-01")
                    .param("maxGraduationDate", "2018-12-31")
                    .param("page", "0")
                    .param("size", "20")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentService).list(Mockito.eq(
                new FilterStudentsRequest(
                    LocalDate.of(2018, 1, 1),
                    LocalDate.of(2018, 12, 31),
                    1L, null, null, null
                )), Mockito.eq(TestUtils.createPageRequest()));
    }

    @Test
    public void getStudent() throws Exception {
        Mockito.when(studentService.findById(Mockito.anyLong())).thenReturn(Optional.of(new Student()));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentService).findById(Mockito.eq(1L));
    }
}
