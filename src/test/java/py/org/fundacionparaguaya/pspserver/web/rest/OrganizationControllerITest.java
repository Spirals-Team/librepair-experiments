package py.org.fundacionparaguaya.pspserver.web.rest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

/**
 * 
 *created by jaime on 9/5/17
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(OrganizationController.class)
@ActiveProfiles("test")
public class OrganizationControllerITest {

    @Autowired
    private OrganizationController controller;

    @MockBean
    private OrganizationService organizationService;

    @Autowired
    private MockMvc mockMvc;

    private OrganizationDTO mockOrganization;

    @Before
    public void setup() {
        mockOrganization = OrganizationDTO.builder().name("foo.name").code("foo.code")
                .description("foo.description").isActive(true).country(getCountryTest())
                .application(getApplicationTest()).information("foo.information").build();
    }

    @Test
    public void requestingPutOrganizationShouldAddNewOrganization() throws Exception {
        when(organizationService.addOrganization(anyObject())).thenReturn(mockOrganization);

        String json = TestHelper.mapToJson(mockOrganization);
        mockMvc.perform(post("/api/v1/organizations").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(mockOrganization.getName())));
    }

    @Test
    public void requestingPostOrganizationShouldUpdateOrganization() throws Exception {
        Long organizationId = 9999L;
        when(organizationService.updateOrganization(eq(organizationId), anyObject())).thenReturn(mockOrganization);

        String json = TestHelper.mapToJson(mockOrganization);
        mockMvc.perform(put("/api/v1/organizations/{organizationId}", organizationId).content(json)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mockOrganization.getName())));

    }

    private CountryDTO getCountryTest() {
        CountryDTO dto = new CountryDTO();
        dto.setId(new Long(1));
        dto.setCountry("foo.COUNTRY");
        return dto;
    }

    private ApplicationDTO getApplicationTest() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(new Long(1));
        dto.setDescription("foo.APPLICATION");
        return dto;
    }
}
