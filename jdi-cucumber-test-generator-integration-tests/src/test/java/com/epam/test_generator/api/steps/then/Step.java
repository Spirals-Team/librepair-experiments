package com.epam.test_generator.api.steps.then;

import static com.epam.test_generator.api.ApiTokenInserter.requestDataAndToken;
import static com.epam.test_generator.api.BddGeneratorApi.getStep;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import com.epam.test_generator.controllers.step.request.StepCreateDTO;
import com.epam.test_generator.controllers.step.request.StepUpdateDTO;
import com.epam.test_generator.controllers.step.response.StepDTO;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import com.epam.test_generator.dto.ValidationErrorsDTO;
import cucumber.api.java.en.Then;

public class Step extends StepBackground {

    @Then("^The step should be created$")
    public void theStepShouldBeCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        Long projectId = testContext.getAndDeleteTestDTO(ProjectDTO.class).getId();
        Long suitId = testContext.getAndDeleteTestDTO(SuitDTO.class).getId();
        Long caseId = testContext.getAndDeleteTestDTO(CaseDTO.class).getId();
        Long stepId = Long.valueOf(response.raResponse().print());
        RestResponse getStepResponce = getStep.call(
            requestDataAndToken(
                d -> {
                    d.pathParams.add("projectId", projectId.toString());
                    d.pathParams.add("suitId", suitId.toString());
                    d.pathParams.add("caseId", caseId.toString());
                    d.pathParams.add("stepId", stepId.toString());
                },
                testContext.getToken()
            )
        );
        StepDTO actualStepDTO = mapper.readValue(getStepResponce.raResponse().print(), StepDTO.class);
        StepCreateDTO expectedStepDTO = testContext.getAndDeleteTestDTO(StepCreateDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(201));
        assertThat(actualStepDTO.getDescription(), equalTo(expectedStepDTO.getDescription()));
        assertThat(actualStepDTO.getComment(), equalTo(expectedStepDTO.getComment()));
        assertThat(actualStepDTO.getType(), equalTo(expectedStepDTO.getType()));
        assertThat(actualStepDTO.getDisplayedStatusName(),
            equalTo(expectedStepDTO.getStatus().toString()));
    }

    @Then("^The step shouldn't be created$")
    public void theStepShouldntBeCreated() throws Throwable {
        RestResponse response = testContext.getResponse();
        testContext.getAndDeleteTestDTO(ProjectDTO.class);
        testContext.getAndDeleteTestDTO(SuitDTO.class);
        testContext.getAndDeleteTestDTO(CaseDTO.class);
        testContext.getAndDeleteTestDTO(StepCreateDTO.class);
        ValidationErrorsDTO actualProjectDTO = mapper
            .readValue(response.raResponse().print(), ValidationErrorsDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(400));
        assertThat(actualProjectDTO.getError(), equalTo(true));
    }

    @Then("^The step should be returned$")
    public void theStepShouldBeReturned() throws Throwable {
        RestResponse response = testContext.getResponse();
        testContext.getAndDeleteTestDTO(ProjectDTO.class);
        testContext.getAndDeleteTestDTO(SuitDTO.class);
        testContext.getAndDeleteTestDTO(CaseDTO.class);
        StepDTO actualStepDTO = mapper.readValue(response.raResponse().print(), StepDTO.class);
        StepDTO expectedStepDTO = testContext.getAndDeleteTestDTO(StepDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(200));
        assertThat(actualStepDTO.getId(), equalTo(expectedStepDTO.getId()));
        assertThat(actualStepDTO.getDescription(), equalTo(expectedStepDTO.getDescription()));
        assertThat(actualStepDTO.getDisplayedStatusName(),
            equalTo(expectedStepDTO.getDisplayedStatusName()));
        assertThat(actualStepDTO.getRowNumber(), equalTo(expectedStepDTO.getRowNumber()));
        assertThat(actualStepDTO.getComment(), equalTo(expectedStepDTO.getComment()));
        assertThat(actualStepDTO.getType(), equalTo(expectedStepDTO.getType()));
    }

    @Then("^The step shouldn't be founded$")
    public void theStepShouldntBeFounded() {
        RestResponse response = testContext.getResponse();
        testContext.getAndDeleteTestDTO(ProjectDTO.class);
        testContext.getAndDeleteTestDTO(SuitDTO.class);
        testContext.getAndDeleteTestDTO(CaseDTO.class);
        testContext.getAndDeleteTestDTO(StepDTO.class);
        assertThat(response.raResponse().getStatusCode(), equalTo(404));
        assertThat(response.raResponse().print(), equalTo(""));
    }

    @Then("^The step should be updated$")
    public void theStepShouldBeUpdated() throws Throwable {
        RestResponse response = testContext.getResponse();
        Long projectId = testContext.getAndDeleteTestDTO(ProjectDTO.class).getId();
        Long suitId = testContext.getAndDeleteTestDTO(SuitDTO.class).getId();
        Long caseId = testContext.getAndDeleteTestDTO(CaseDTO.class).getId();
        StepUpdateDTO stepUpdateDTO = testContext.getAndDeleteTestDTO(StepUpdateDTO.class);
        StepDTO oldStepDTO = testContext.getAndDeleteTestDTO(StepDTO.class);
        RestResponse responseNewCase = getStep.call(
            requestDataAndToken(
                d -> {
                    d.pathParams.add("projectId", projectId.toString());
                    d.pathParams.add("suitId", suitId.toString());
                    d.pathParams.add("caseId", caseId.toString());
                    d.pathParams.add("stepId", oldStepDTO.getId().toString());
                },
                testContext.getToken()
            )
        );
        StepDTO actualStepDTO = mapper
            .readValue(responseNewCase.raResponse().print(), StepDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(200));
        assertThat(actualStepDTO.getId(), equalTo(oldStepDTO.getId()));
        assertThat(actualStepDTO.getDescription(), equalTo(stepUpdateDTO.getDescription()));
        assertThat(actualStepDTO.getDisplayedStatusName(),
            equalTo(stepUpdateDTO.getStatus().toString()));
        assertThat(actualStepDTO.getRowNumber(), equalTo(stepUpdateDTO.getRowNumber()));
        assertThat(actualStepDTO.getComment(), equalTo(stepUpdateDTO.getComment()));
        assertThat(actualStepDTO.getType(), equalTo(stepUpdateDTO.getType()));
    }

    @Then("^The step shouldn't be updated$")
    public void theStepShouldntBeUpdated() throws Throwable {
        RestResponse response = testContext.getResponse();
        testContext.getAndDeleteTestDTO(ProjectDTO.class);
        testContext.getAndDeleteTestDTO(SuitDTO.class);
        testContext.getAndDeleteTestDTO(CaseDTO.class);
        testContext.getAndDeleteTestDTO(StepDTO.class);
        testContext.getAndDeleteTestDTO(StepUpdateDTO.class);
        ValidationErrorsDTO actualProjectDTO = mapper
            .readValue(response.raResponse().print(), ValidationErrorsDTO.class);

        assertThat(response.raResponse().getStatusCode(), equalTo(400));
        assertThat(actualProjectDTO.getError(), equalTo(true));
    }

    @Then("^The step should be deleted$")
    public void theStepShouldBeDeleted() {
        RestResponse response = testContext.getResponse();
        assertThat(response.raResponse().getStatusCode(), equalTo(200));
        assertThat(response.raResponse().print(), equalTo(""));

        Long projectId = testContext.getAndDeleteTestDTO(ProjectDTO.class).getId();
        Long suitId = testContext.getAndDeleteTestDTO(SuitDTO.class).getId();
        Long caseId = testContext.getAndDeleteTestDTO(CaseDTO.class).getId();
        Long stepId = testContext.getAndDeleteTestDTO(StepDTO.class).getId();
        RestResponse oldStepResponse = getStep.call(
            requestDataAndToken(
                d -> {
                    d.pathParams.add("projectId", projectId.toString());
                    d.pathParams.add("suitId", suitId.toString());
                    d.pathParams.add("caseId", caseId.toString());
                    d.pathParams.add("stepId", stepId.toString());
                },
                testContext.getToken()
            )
        );
        assertThat(oldStepResponse.raResponse().getStatusCode(), equalTo(404));
    }

}
