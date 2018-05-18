package com.epam.test_generator.api.steps.then;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.caze.request.CaseCreateDTO;
import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.controllers.project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import com.epam.test_generator.controllers.suit.request.SuitCreateDTO;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import cucumber.api.java.en.Then;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AssertStepsDef extends StepBackground {

    @Then("^The project should be created$")
    public void theProjectShouldBeCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        ProjectDTO actualProjectDTO = mapper.readValue(response.raResponse().print(), ProjectDTO.class);
        ProjectCreateDTO expectedProjectDTO = testContext.getAndDeleteTestDTO(ProjectCreateDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(201));
        assertThat("The name of created object is not as expected",
                actualProjectDTO.getName(), equalTo(expectedProjectDTO.getName()));
        assertThat("The description of created object not as expected",
                actualProjectDTO.getDescription(), equalTo(expectedProjectDTO.getDescription()));
    }

    @Then("^The suit should be created$")
    public void theSuitShouldBeCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        SuitDTO actualSuitDTO = mapper.readValue(response.raResponse().print(), SuitDTO.class);
        SuitCreateDTO expectedSuitDTO = testContext.getAndDeleteTestDTO(SuitCreateDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(201));
        assertThat("The name of created object is not as expected",
                actualSuitDTO.getName(), equalTo(expectedSuitDTO.getName()));
        assertThat("The description of created object not as expected",
                actualSuitDTO.getDescription(), equalTo(expectedSuitDTO.getDescription()));
    }

    @Then("^The case should be created$")
    public void theCaseShouldBeCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        CaseDTO actualCaseDTO = mapper.readValue(response.raResponse().print(), CaseDTO.class);
        CaseCreateDTO expectedCaseDTO = testContext.getAndDeleteTestDTO(CaseCreateDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(201));
        assertThat("The name of created object is not as expected",
                actualCaseDTO.getName(), equalTo(expectedCaseDTO.getName()));
        assertThat("The description of created object not as expected",
                actualCaseDTO.getDescription(), equalTo(expectedCaseDTO.getDescription()));
    }
}
