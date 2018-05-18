package com.epam.test_generator.api.steps.then;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import com.epam.test_generator.controllers.user.request.LoginUserDTO;
import com.epam.test_generator.controllers.user.response.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import cucumber.api.java.en.Then;
import java.util.List;
import org.springframework.http.HttpStatus;

public class TestLead_AssignedProjects extends StepBackground {

    @Then("^I get projects$")
    public void userIsCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        List<ProjectDTO> actualProjectDTOs = mapper
            .readValue(response.raResponse().print(), new TypeReference<List<ProjectDTO>>() {
            });

        assertThat(response.raResponse().getStatusCode(), equalTo(HttpStatus.OK.value()));
        if (actualProjectDTOs.size() > 0) {
            String email = testContext.getTestDTO(LoginUserDTO.class).getEmail();
            boolean userAssign = false;
            for (ProjectDTO dto : actualProjectDTOs) {
                for (UserDTO user : dto.getUsers()) {
                    userAssign = user.getEmail().equals(email);
                    if (userAssign) {
                        break;
                    }
                }
                assertTrue(userAssign);
            }
        }
    }

}
