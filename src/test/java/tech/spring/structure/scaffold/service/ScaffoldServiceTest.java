package tech.spring.structure.scaffold.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static tech.spring.structure.scaffold.ScaffoldIntegrationHelper.assertScaffold;
import static tech.spring.structure.scaffold.ScaffoldIntegrationHelper.assertScaffolding;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.spring.structure.auth.evaluator.StructureSecurityExpressionEvaluator;
import tech.spring.structure.scaffold.config.ScaffoldConfig;
import tech.spring.structure.scaffold.model.Scaffold;

@RunWith(SpringRunner.class)
public class ScaffoldServiceTest {

    @Value("classpath:mock/scaffold-config.json")
    private Resource scaffoldConfigResource;

    @Value("classpath:mock/scaffolding.json")
    private Resource scaffoldResource;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private StructureSecurityExpressionEvaluator securityExpressionEvaluator;

    @InjectMocks
    private ScaffoldService scaffoldService;

    private List<Scaffold> mockScaffolding;

    @Before
    public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        ScaffoldConfig scaffoldConfig = objectMapper.readValue(scaffoldConfigResource.getFile(), ScaffoldConfig.class);
        setField(scaffoldService, "scaffoldConfig", scaffoldConfig);
        when(securityExpressionEvaluator.evaluate(any(String.class), any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(true);
        scaffoldService.scanEntities();
        // @formatter:off
        mockScaffolding = objectMapper.readValue(scaffoldResource.getFile(), new TypeReference<List<Scaffold>>() {});
        // @formatter:on
    }

    @Test
    public void testGet() throws JsonParseException, JsonMappingException, IOException {
        List<Scaffold> scaffolding = scaffoldService.get(new MockHttpServletRequest(), new MockHttpServletResponse());
        // @formatter:off
        scaffolding = objectMapper.readValue(objectMapper.writeValueAsString(scaffolding), new TypeReference<List<Scaffold>>() {});
        // @formatter:on
        assertScaffolding(mockScaffolding, scaffolding);
    }

    @Test
    public void testGetByModel() throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        Optional<Scaffold> scaffold = scaffoldService.get(new MockHttpServletRequest(), new MockHttpServletResponse(), "MockModel");
        assertTrue("Could not get scaffold!", scaffold.isPresent());
        // @formatter:off
        scaffold = Optional.of(objectMapper.readValue(objectMapper.writeValueAsString(scaffold.get()), Scaffold.class));
        // @formatter:on
        Optional<Scaffold> mockScaffold = mockScaffolding.stream().filter(s -> s.getName().equals("MockModel")).findAny();
        assertTrue("Unable to find scaffold with name MockModel!", mockScaffold.isPresent());
        assertScaffold(mockScaffold.get(), scaffold.get());
    }

}
