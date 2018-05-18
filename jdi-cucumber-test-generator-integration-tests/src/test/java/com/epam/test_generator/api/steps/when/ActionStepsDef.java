package com.epam.test_generator.api.steps.when;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.caze.request.CaseCreateDTO;
import com.epam.test_generator.controllers.project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import com.epam.test_generator.controllers.suit.request.SuitCreateDTO;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import cucumber.api.java.en.When;

import static com.epam.test_generator.api.ApiTokenInserter.requestBodyAndToken;
import static com.epam.test_generator.api.ApiTokenInserter.requestDataAndToken;
import static com.epam.test_generator.api.BddGeneratorApi.addCaseToSuit;
import static com.epam.test_generator.api.BddGeneratorApi.createProject;
import static com.epam.test_generator.api.BddGeneratorApi.createSuit;

public class ActionStepsDef extends StepBackground {

    //    @When("^I send POST request with (.*)")
    @When("^I send POST request with project")
    public void iSendPOSTRequestWithProjectData() throws Throwable {
        String projectDTOAsString = mapper.writeValueAsString(testContext.getTestDTO(ProjectCreateDTO.class));
        RestResponse response = createProject.call(requestBodyAndToken(projectDTOAsString, testContext.getToken()));
        testContext.setResponse(response);
    }

    @When("^I send POST request with suit")
    public void iSendPOSTRequestWithSuitData() throws Throwable {
        String suitDTOAsString = mapper.writeValueAsString(testContext.getTestDTO(SuitCreateDTO.class));
        RestResponse response = createSuit.call(
            requestDataAndToken(d -> {
                    d.pathParams.add("projectId", testContext.getTestDTO(ProjectDTO.class).getId().toString()); //using ID of project created in previous step
                    d.body = suitDTOAsString;
                },
                testContext.getToken())
        );
        testContext.setResponse(response);
    }

    @When("^I send POST request with case")
    public void iSendPOSTRequestWithCaseData() throws Throwable {
        String caseDTOAsString = mapper.writeValueAsString(testContext.getTestDTO(CaseCreateDTO.class));
        RestResponse response = addCaseToSuit.call(requestDataAndToken(d -> {
                    d.pathParams.add("projectId", testContext.getTestDTO(ProjectDTO.class).getId().toString());
                    d.pathParams.add("suitId", testContext.getTestDTO(SuitDTO.class).getId().toString());
                    d.body = caseDTOAsString;
                }, testContext.getToken()
        ));
        testContext.setResponse(response);
    }
}
