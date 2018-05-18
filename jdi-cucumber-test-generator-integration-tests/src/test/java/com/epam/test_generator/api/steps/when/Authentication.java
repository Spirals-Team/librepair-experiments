package com.epam.test_generator.api.steps.when;

import static com.epam.http.requests.RequestData.requestBody;
import static com.epam.test_generator.api.BddGeneratorApi.loginUsingPOST;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.user.request.LoginUserDTO;
import cucumber.api.java.en.When;

public class Authentication extends StepBackground {

    @When("^I sign in$")
    public void admLoginRequest() throws Throwable {
        String loginUserDTOAsString = mapper
            .writeValueAsString(testContext.getTestDTO(LoginUserDTO.class));
        RestResponse response = loginUsingPOST.call(requestBody(loginUserDTOAsString));
        testContext.setResponse(response);
    }
}
