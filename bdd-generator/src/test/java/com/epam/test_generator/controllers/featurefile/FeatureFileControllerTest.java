package com.epam.test_generator.controllers.featurefile;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.test_generator.controllers.GlobalExceptionController;
import com.epam.test_generator.controllers.caze.CaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class FeatureFileControllerTest {

    private ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;

    private static final long SIMPLE_PROJECT_ID = 0L;
    private static final long SIMPLE_SUIT_ID = 1L;
    private static final long SIMPLE_CASE_ID = 2L;
    private static final Long[] CASE_IDS = {3L, 4L, 5L};

    @InjectMocks
    private FeatureFileController featureFileController;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(featureFileController)
            .setControllerAdvice(new GlobalExceptionController())
            .build();
    }

    @Test
    public void downloadFile() throws Exception {

        mockMvc.perform(
            post("/projects/" + SIMPLE_PROJECT_ID + "/feature-file"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id", is((int) SIMPLE_CASE_ID)));
            /*.andExpect(jsonPath("$.name", is(caseDTO.getName())))
            .andExpect(jsonPath("$.displayedStatusName", is(caseDTO.getDisplayedStatusName())));*/
    }
}