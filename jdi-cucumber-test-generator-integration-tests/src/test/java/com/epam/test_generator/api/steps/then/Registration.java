package com.epam.test_generator.api.steps.then;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.user.request.RegistrationUserDTO;
import com.epam.test_generator.controllers.user.response.UserDTO;
import cucumber.api.java.en.Then;
import org.springframework.http.HttpStatus;

public class Registration extends StepBackground {

    @Then("^User is created$")
    public void userIsCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        UserDTO actualRegistrationUserDTO = mapper
            .readValue(response.raResponse().print(), UserDTO.class);
        RegistrationUserDTO expectedRegistrationUserDTO = testContext
            .getAndDeleteTestDTO(RegistrationUserDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(HttpStatus.OK.value()));

        assertThat(actualRegistrationUserDTO.getName(),
            equalTo(expectedRegistrationUserDTO.getName()));

        assertThat(actualRegistrationUserDTO.getSurname(),
            equalTo(expectedRegistrationUserDTO.getSurname()));

        assertThat(actualRegistrationUserDTO.getRole(), equalTo("GUEST"));

        assertThat(actualRegistrationUserDTO.getEmail(),
            equalTo(expectedRegistrationUserDTO.getEmail()));
    }

}
