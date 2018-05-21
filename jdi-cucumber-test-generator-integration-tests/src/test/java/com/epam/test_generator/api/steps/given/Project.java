package com.epam.test_generator.api.steps.given;

import static com.epam.test_generator.api.ApiTokenInserter.requestBodyAndToken;
import static com.epam.test_generator.api.BddGeneratorApi.createProject;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

public class Project extends StepBackground {

    private final static int FIRST_INDEX = 0;

    @Given("^I have a project$")
    public void iHaveAProject(DataTable dataTable) throws Throwable {
        String projectDTOAsString = mapper
            .writeValueAsString(dataTable.asList(ProjectCreateDTO.class).get(FIRST_INDEX));
        RestResponse response = createProject
            .call(requestBodyAndToken(projectDTOAsString, testContext.getToken()));
        ProjectDTO projectDTO = mapper.readValue(response.raResponse().print(), ProjectDTO.class);
        testContext.setTestDTO(projectDTO);
    }

    @Given("^I have a project that doesn't exist in data base$")
    public void iHaveAProjectThatDoesntExist(DataTable dataTable) {
        testContext.setTestDTO(dataTable.asList(ProjectDTO.class).get(FIRST_INDEX));
    }

}
