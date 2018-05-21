package com.epam.test_generator.api.steps.given;

import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.user.request.LoginUserDTO;
import cucumber.api.java.en.Given;

public class Authentication extends StepBackground {

    @Given("^I am Admin$")
    public void logAdmin(){
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail(testContext.ADMIN_EMAIL);
        loginUserDTO.setPassword(testContext.ADMIN_PASSWORD);
        testContext.setTestDTO(loginUserDTO);
    }
}
