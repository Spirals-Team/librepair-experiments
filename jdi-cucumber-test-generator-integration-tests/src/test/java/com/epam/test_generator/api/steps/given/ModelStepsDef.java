package com.epam.test_generator.api.steps.given;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.caze.request.CaseCreateDTO;
import com.epam.test_generator.controllers.project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import com.epam.test_generator.controllers.suit.request.SuitCreateDTO;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import cucumber.api.java.en.Given;

import java.util.HashSet;

import static com.epam.test_generator.api.ApiTokenInserter.requestBodyAndToken;
import static com.epam.test_generator.api.ApiTokenInserter.requestDataAndToken;
import static com.epam.test_generator.api.BddGeneratorApi.createProject;
import static com.epam.test_generator.api.BddGeneratorApi.createSuit;

public class ModelStepsDef extends StepBackground {

    @Given("^I have a project object$")
    public void iHaveARequestProjectDTOObject() throws Throwable {
        ProjectCreateDTO projectDTO = new ProjectCreateDTO();
        projectDTO.setName("Project name");
        projectDTO.setDescription("Project description");

        testContext.setTestDTO(projectDTO);
    }  // map -> (object, dto)

    @Given("^I have a suit object$")
    public void iHaveARequestSuitDTOObject() throws Throwable {
        SuitCreateDTO suitDTO = new SuitCreateDTO();
        suitDTO.setName("Suit name");
        suitDTO.setDescription("Suit description");
        suitDTO.setPriority(1);

        testContext.setTestDTO(suitDTO);
    }

    @Given("^I have a case object$")
    public void iHaveARequestCaseDTOObject() throws Throwable {
        CaseCreateDTO caseDTO = new CaseCreateDTO();
        caseDTO.setName("Case name");
        caseDTO.setDescription("Case description");
        caseDTO.setPriority(1);
        caseDTO.setTags(new HashSet<>());

        testContext.setTestDTO(caseDTO);
    }

    @Given("^I create new project$")
    public void iHaveARequestSuitDTOObjectWithProject() throws Throwable {
        iHaveARequestProjectDTOObject();
        String projectDTOAsString = mapper.writeValueAsString(testContext.getTestDTO(ProjectCreateDTO.class));
        RestResponse response = createProject.call(requestBodyAndToken(projectDTOAsString, testContext.getToken()));
        testContext.setResponse(response);
        ProjectDTO actualProjectDTO = mapper.readValue(response.raResponse().print(), ProjectDTO.class);
        testContext.setTestDTO(actualProjectDTO);
    }

    @Given("^I create new suit$")
    public void iHaveARequestCaseDTOObjectWithSuit() throws Throwable {
        iHaveARequestSuitDTOObject();
        String suitDTOAsString = mapper.writeValueAsString(testContext.getTestDTO(SuitCreateDTO.class));
        RestResponse response = createSuit.call(requestDataAndToken(d -> {
                    d.pathParams.add("projectId", testContext.getTestDTO(ProjectDTO.class).getId().toString()); //using ID of project created in previous step
                    d.body = suitDTOAsString;
                }, testContext.getToken()
        ));
        testContext.setResponse(response);
        SuitDTO actualSuitDTO = mapper.readValue(response.raResponse().print(), SuitDTO.class);
        testContext.setTestDTO(actualSuitDTO);
    }
}
