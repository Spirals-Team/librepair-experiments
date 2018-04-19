package pl.rmitula.restfullshop.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void GetAllCategoriesTest() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.is("shoes")))
                .andExpect(jsonPath("$.[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$.[1].name", Matchers.is("t-shirts")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

    @Test
    public void GetCategoryByIdTest() throws Exception {
        mockMvc.perform(get("/api/categories/{id}", 2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.name", Matchers.is("t-shirts")));
    }

    @Test
    public void GetCategoryByIdWithUnknownIdWTest() throws Exception {
        mockMvc.perform(get("/api/categories/{id}", 100))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with id: 100")));
    }

    @Test
    public void GetCategoryByNameTest() throws Exception {
        mockMvc.perform(get("/api/categories/findByName/{name}", "t-shirts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.name", Matchers.is("t-shirts")));
    }

    @Test
    public void GetCategoryByNameWithIgnoreCaseTest() throws Exception {
        mockMvc.perform(get("/api/categories/findByName/{name}", "t-sHIRtS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.name", Matchers.is("t-shirts")));
    }

    @Test
    public void GetCategoryByNameWithUnknownNameTest() throws Exception {
        mockMvc.perform(get("/api/categories/findByName/{name}", "UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with name: UNKNOWN")));
    }

    @Test
    public void CreateCategoryTest() throws Exception {
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"newCategory\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void CreateCategoryWithExistingNameTest() throws Exception {
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"SHOES\" }"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(409)))
                .andExpect(jsonPath("$.description", Matchers.is("This category already exists.")));
    }

    @Test
    public void UpdateCategoryTest() throws Exception {
        mockMvc.perform(put("/api/categories/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cool shoes\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateCategoryWithExistingNameFromAnotherCategoryTest() throws Exception {
        mockMvc.perform(put("/api/categories/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"t-shirts\" }"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(409)))
                .andExpect(jsonPath("$.description", Matchers.is("This name is already associated with another category.")));
    }

    @Test
    public void UpdateCategoryWithUnknownIdTest() throws Exception {
        mockMvc.perform(put("/api/categories/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"t-shirts\" }"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with id: 100")));
    }

    @Test
    public void DeleteCategoryTest() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void DeleteCategoryWithUnknownIdTest() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 100))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with id: 100")));
    }






}