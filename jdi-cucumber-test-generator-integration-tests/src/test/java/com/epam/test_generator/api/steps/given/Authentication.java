package com.epam.test_generator.api.steps.given;

import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.user.request.LoginUserDTO;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

import java.util.List;

public class Authentication extends StepBackground {

    @Given("^I enter User data$")
    public void logAdmin(DataTable table) {
        List<LoginUserDTO> loginUserDTOs = table.asList(LoginUserDTO.class);
        testContext.setTestDTO(loginUserDTOs.get(0));
    }

}
