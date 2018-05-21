package com.epam.test_generator.api.steps.given;

import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.api.steps.utils.Basics;
import cucumber.api.java.en.Given;

public class RoleChanger extends StepBackground {
    private static Basics basics = new Basics();

    @Given("^I logged in as admin")
    public void iLoggedInAsAdmin() throws Throwable {
        basics.loginAdmin();
    }

    @Given("^I have new user")
    public void iHaveNewUser() throws Throwable {
        basics.registerUser();
    }
}
