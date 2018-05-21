package com.epam.test_generator.api.steps.given;

import static com.epam.test_generator.api.ApiTokenInserter.requestDataAndToken;
import static com.epam.test_generator.api.BddGeneratorApi.addCaseToSuit;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.caze.request.CaseCreateDTO;
import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.controllers.project.response.ProjectDTO;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import java.util.HashSet;

public class Case extends StepBackground {

    private static final int FIRST_INDEX = 0;

    @Given("^I have a case$")
    public void iHaveACase(DataTable dataTable) throws Throwable {
        Long projectId = testContext.getTestDTO(ProjectDTO.class).getId();
        Long suitId = testContext.getTestDTO(SuitDTO.class).getId();
        CaseCreateDTO caseCreateDTO = dataTable.asList(CaseCreateDTO.class).get(FIRST_INDEX);
        caseCreateDTO.setTags(testContext.getAndDeleteTestDTO(HashSet.class));
        String caseCreateDTOAsString = mapper.writeValueAsString(caseCreateDTO);
        RestResponse response = addCaseToSuit.call(
            requestDataAndToken(d -> {
                d.body = caseCreateDTOAsString;
                d.pathParams.add("projectId", projectId.toString());
                d.pathParams.add("suitId", suitId.toString());
            }, testContext.getToken())
        );
        CaseDTO caseDTO = mapper.readValue(response.raResponse().print(), CaseDTO.class);
        testContext.setTestDTO(caseDTO);
    }

    @Given("^I have a case that doesn't exist in data base$")
    public void iHaveACaseThatDoesntExist(DataTable dataTable) {
        testContext.setTestDTO(dataTable.asList(CaseDTO.class).get(FIRST_INDEX));
    }
}
