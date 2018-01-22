package py.org.fundacionparaguaya.pspserver.web.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.util.TestHelper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rodrigovillalba on 11/6/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SnapshotController.class)
@ActiveProfiles("test")
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class SnapshotControllerTest {

    private static final Long SURVEY_ID = 1L;
    private static final Long SNAPSHOT_ID = 2L;
    private static final Long USER_ID = 3L;
    private static final Long TERM_COND_ID = 4L;
    private static final Long PRIV_POL_ID = 5L;
    private static final String SNAPSHOT_JSON_FILE = "/snapshot.json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SnapshotController controller;

    @MockBean
    private SnapshotService service;

    @Test
    public void shouldGetAllSnapshotsBySurvey() throws Exception {
        List<Snapshot> snapshots = snapshotList();
        when(service.find(eq(SURVEY_ID), anyLong())).thenReturn(snapshots);

        this.mockMvc.perform(get("/api/v1/snapshots").param("survey_id", SURVEY_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("snapshots-list",
                        preprocessResponse(prettyPrint()),
                        responseFields(snapshotsDescriptor),
                        requestParameters(parameterWithName("survey_id").description("The survey id that this snapshot belongs to"))));
    }


    @Test
    public void shouldPostToCreateSnapshot() throws Exception {
        Snapshot snapshot = getSnapshot();

        when(service.addSurveySnapshot(anyObject(), anyObject())).thenReturn(snapshot);

        String content = TestHelper.mapToJson(snapshot);
        this.mockMvc.perform(
                post("/api/v1/snapshots")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("snapshots-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(snapshotDescriptor)));
    }

    private List<Snapshot> snapshotList() {
        return Arrays.asList(getSnapshot());
    }


    public Snapshot getSnapshot() {
        Snapshot snapshot = (Snapshot) TestHelper.mapToObjectFromFile(SNAPSHOT_JSON_FILE, Snapshot.class);
        return new Snapshot()
                .surveyId(SURVEY_ID)
                .snapshotEconomicId(SNAPSHOT_ID)
                .userId(USER_ID)
                .termCondId(TERM_COND_ID)
                .privPolId(PRIV_POL_ID)
                .personalSurveyData(snapshot.getPersonalSurveyData())
                .economicSurveyData(snapshot.getEconomicSurveyData())
                .indicatorSurveyData(snapshot.getIndicatorSurveyData());
    }

    FieldDescriptor[] snapshotDescriptor = new FieldDescriptor[]{
            fieldWithPath("survey_id").type(JsonFieldType.NUMBER)
                    .description("The survey's id that this snapshot belongs to"),
            fieldWithPath("snapshot_economic_id").type(JsonFieldType.NUMBER)
                    .description("The id that this snapshot belongs to"),
            fieldWithPath("personal_survey_data").type(JsonFieldType.OBJECT)
                    .description("Key/value pairs representing the filled out 'Personal Information' section of the survey"),
            fieldWithPath("economic_survey_data").type(JsonFieldType.OBJECT)
                    .description("Key/value pairs representing the filled out 'Socio Economical' section of the survey"),
            fieldWithPath("indicator_survey_data").type(JsonFieldType.OBJECT)
                    .description("Key/value pairs representing the filled out 'Indicators' section of the survey"),
            fieldWithPath("created_at").type(JsonFieldType.NULL)
                    .description("ISO 8601 formatted creation date"),
            fieldWithPath("user_id").type(JsonFieldType.NUMBER)
                    .description("The user's id"),
            fieldWithPath("term_cond_id").type(JsonFieldType.NUMBER)
                    .description("The terms and conditions id"),
            fieldWithPath("priv_pol_id").type(JsonFieldType.NUMBER)
                    .description("The privacy polities id")
    };
    FieldDescriptor[] snapshotsDescriptor = new FieldDescriptor[]{
            fieldWithPath("[].survey_id").type(JsonFieldType.NUMBER)
                    .description("The survey's id that this snapshot belongs to"),
            fieldWithPath("[].snapshot_economic_id").type(JsonFieldType.NUMBER)
                    .description("The id that this snapshot belongs to"),
            fieldWithPath("[].personal_survey_data").type(JsonFieldType.OBJECT)
                    .description("Key/value pairs representing the filled out 'Personal Information' section of the survey"),
            fieldWithPath("[].economic_survey_data").type(JsonFieldType.OBJECT)
                    .description("Key/value pairs representing the filled out 'Socio Economical' section of the survey"),
            fieldWithPath("[].indicator_survey_data").type(JsonFieldType.OBJECT)
                    .description("Key/value pairs representing the filled out 'Indicators' section of the survey"),
            fieldWithPath("[].created_at").type(JsonFieldType.NULL)
                    .description("ISO 8601 formatted creation date"),
            fieldWithPath("[].user_id").type(JsonFieldType.NUMBER)
                    .description("The user's id"),
            fieldWithPath("[].term_cond_id").type(JsonFieldType.NUMBER)
                    .description("The terms and conditions id"),
            fieldWithPath("[].priv_pol_id").type(JsonFieldType.NUMBER)
                    .description("The privacy polities id")
    };

}
