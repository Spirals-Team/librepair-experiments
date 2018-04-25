package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.TestUtils;
import ar.edu.undav.semillero.domain.entity.Company;
import ar.edu.undav.semillero.request.CreateCompanyRequest;
import ar.edu.undav.semillero.request.FilterCompaniesRequest;
import ar.edu.undav.semillero.service.CompanyService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CompanyController.class)
@Import({SpringDataWebAutoConfiguration.class, SecurityConfig.class})
@WithMockUser
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CompanyService companyService;

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(companyService);
    }

    // POST Tests
    @Test
    public void saveCompanyNotPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/company"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    public void saveCompanyPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/company"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void saveCompanyPostParams() throws Exception {
        String name = "ECORP";
        String contact = "Carlos";
        String email = "carlos@ecorp.com";
        String comments = "a veces es medio colgado este Carlos";
        CreateCompanyRequest request = new CreateCompanyRequest(name, contact, email, comments);
        Company company = new Company(name, contact, email, comments);
        TestUtils.setId(company, 1L);
        Mockito.when(companyService.save(Mockito.any(CreateCompanyRequest.class))).thenReturn(company);
        mockMvc.perform(MockMvcRequestBuilders.post("/company").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact", Matchers.is(contact)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments", Matchers.is(comments)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.interviews", Matchers.empty()));
        Mockito.verify(companyService).save(Mockito.eq(request));
    }

    @Test
    public void saveCompanyPostParamsInvalidRequest() throws Exception {
        CreateCompanyRequest request = new CreateCompanyRequest("", "", "", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/company").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateCompany() throws Exception {
        CreateCompanyRequest request = new CreateCompanyRequest("new name", "new contact", "new@mail.com", "new comments");
        Company company = new Company("original name", "original contact", "old@mail.com", "original comments");
        TestUtils.setId(company, 1L);
        Mockito.when(companyService.update(Mockito.anyLong(), Mockito.any(CreateCompanyRequest.class))).thenReturn(Optional.of(company));
        mockMvc.perform(MockMvcRequestBuilders.put("/company/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.notNullValue(String.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact", Matchers.notNullValue(String.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.notNullValue(String.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments", Matchers.notNullValue(String.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.interviews", Matchers.empty()));
        Mockito.verify(companyService).update(Mockito.eq(1L), Mockito.eq(request));
    }

    @Test
    public void updateCompanyNotFound() throws Exception {
        CreateCompanyRequest request = new CreateCompanyRequest("new name", "new contact", "new@mail.com", "new comments");
        Mockito.when(companyService.update(Mockito.anyLong(), Mockito.any(CreateCompanyRequest.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.put("/company/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
        Mockito.verify(companyService).update(Mockito.eq(1L), Mockito.eq(request));
    }

    // GET Tests

    @Test
    public void getAllCompanies() throws Exception {
        Mockito.when(companyService.list(Mockito.any(FilterCompaniesRequest.class), Mockito.any(Pageable.class))).thenReturn(TestUtils.createCompanyDTOPage());
        mockMvc.perform(MockMvcRequestBuilders.get("/company"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(companyService).list(Mockito.eq(new FilterCompaniesRequest(null, false, false)), Mockito.eq(TestUtils.createPageRequest()));
    }

    @Test
    public void getCompany() throws Exception {
        Mockito.when(companyService.list(Mockito.any(FilterCompaniesRequest.class), Mockito.any(Pageable.class))).thenReturn(TestUtils.createCompanyDTOPage());
        mockMvc.perform(MockMvcRequestBuilders.get("/company").param("name", "ECORP"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(companyService).list(Mockito.eq(new FilterCompaniesRequest("ECORP", false, false)), Mockito.eq(TestUtils.createPageRequest()));
    }

    @Test
    public void getCompanyWrongParam() throws Exception {
        Mockito.when(companyService.list(Mockito.any(FilterCompaniesRequest.class), Mockito.any(Pageable.class))).thenReturn(TestUtils.createCompanyDTOEmptyPage());
        mockMvc.perform(MockMvcRequestBuilders.get("/company").param("name", "dfghertyafdgaert"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
        Mockito.verify(companyService).list(Mockito.eq(new FilterCompaniesRequest("dfghertyafdgaert", false, false)), Mockito.eq(TestUtils.createPageRequest()));
    }

    @Test
    public void getCompanyById() throws Exception {
        Mockito.when(companyService.findById(Mockito.anyLong())).thenReturn(Optional.of(new Company()));
        mockMvc.perform(MockMvcRequestBuilders.get("/company/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(companyService).findById(Mockito.eq(1L));
    }

    @Test
    public void getCompanyByIdNotFound() throws Exception {
        Mockito.when(companyService.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/company/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        Mockito.verify(companyService).findById(Mockito.eq(1L));
    }
}
