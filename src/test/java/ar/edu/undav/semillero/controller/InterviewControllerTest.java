package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.request.CreateInterviewRequest;
import ar.edu.undav.semillero.service.InterviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InterviewController.class)
@Import(SpringDataWebAutoConfiguration.class)
public class InterviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private InterviewService interviewService;

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(interviewService);
    }

    // POST Tests
    @Test
    public void saveInterviewNotPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/interview"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    public void saveInterviewPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/interview"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void saveInterviewPostParams() throws Exception {
        long studentId = 1001;
        long companyId = 1001;
        CreateInterviewRequest request = new CreateInterviewRequest(studentId, companyId);
        mockMvc.perform(MockMvcRequestBuilders.post("/interview").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(interviewService).save(Mockito.eq(request));
    }

    @Test
    public void saveInterviewPostParamsInvalid() throws Exception {
        CreateInterviewRequest request = new CreateInterviewRequest(null, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/interview").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // GET Tests

    @Test
    public void getAllInterviews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/interview"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(interviewService).findAll();
    }

    @Test
    public void getAllInterviewsOrderDesc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/interview").param("desc", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(interviewService).findAllOrderByIdDesc();
    }

    @Test
    public void getAllInterviewsByStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/interview").param("student", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(interviewService).findByStudent(Mockito.eq(1L));
    }

    @Test
    public void getAllInterviewsByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/interview").param("when", "2018-04-03"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(interviewService).findByDate(Mockito.eq(LocalDate.of(2018, 4, 3)));
    }
}
