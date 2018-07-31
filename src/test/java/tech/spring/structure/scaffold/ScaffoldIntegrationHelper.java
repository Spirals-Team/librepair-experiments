package tech.spring.structure.scaffold;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import tech.spring.structure.auth.AuthIntegrationHelper;
import tech.spring.structure.scaffold.model.Property;
import tech.spring.structure.scaffold.model.Scaffold;
import tech.spring.structure.scaffold.model.Validation;

public abstract class ScaffoldIntegrationHelper extends AuthIntegrationHelper {

    @Value("classpath:mock/scaffolding.json")
    private Resource scaffoldResource;

    private List<Scaffold> scaffolding;

    @Before
    public void initializeMockScaffold() throws JsonParseException, JsonMappingException, IOException {
        // @formatter:off
        scaffolding = objectMapper.readValue(scaffoldResource.getFile(), new TypeReference<List<Scaffold>>() {});
        // @formatter:on
    }

    protected Scaffold getMockScaffold(String name) {
        Optional<Scaffold> scaffold = scaffolding.stream().filter(s -> s.getName().equals(name)).findAny();
        assertTrue("Unable to find scaffold with name " + name + "!", scaffold.isPresent());
        return scaffold.get();
    }

    protected List<Scaffold> getMockScaffoldingAsAnonymous() {
        return new ArrayList<Scaffold>() {
            private static final long serialVersionUID = 1L;
            {
                add(getMockScaffold("LoginRequest"));
            }
        };
    }

    protected List<Scaffold> getMockScaffoldingAsUser() {
        return new ArrayList<Scaffold>() {
            private static final long serialVersionUID = 1L;
            {
                add(getMockScaffold("LoginRequest"));
                add(getMockScaffold("MenuItem"));
            }
        };
    }

    protected List<Scaffold> getMockScaffoldingAsAdmin() {
        return new ArrayList<Scaffold>() {
            private static final long serialVersionUID = 1L;
            {
                add(getMockScaffold("LoginRequest"));
                add(getMockScaffold("MenuItem"));
            }
        };
    }

    protected List<Scaffold> getMockScaffoldingAsSuperadmin() {
        return new ArrayList<Scaffold>() {
            private static final long serialVersionUID = 1L;
            {
                add(getMockScaffold("LoginRequest"));
                add(getMockScaffold("MenuItem"));
                add(getMockScaffold("MockModel"));
            }
        };
    }

    public static void assertScaffolding(List<Scaffold> mockScaffolding, List<Scaffold> scaffolding) {
        assertEquals("Scaffolding did not have expected number of scaffolds!", mockScaffolding.size(), scaffolding.size());
        for (int i = 0; i < mockScaffolding.size(); i++) {
            Scaffold mockScaffold = mockScaffolding.get(i);
            Optional<Scaffold> scaffold = scaffolding.stream().filter(s -> s.getName().equals(mockScaffold.getName())).findAny();
            assertTrue("Scaffolding does not have expected scaffold!", scaffold.isPresent());
            assertScaffold(mockScaffold, scaffold.get());
        }
    }

    public static void assertScaffold(Scaffold mockScaffold, Scaffold scaffold) {
        assertEquals("Scaffold does not have correct name!", mockScaffold.getName(), scaffold.getName());
        assertEquals("Scaffold does not have correct authorization!", mockScaffold.getAuthorization(), scaffold.getAuthorization());
        assertProperties(mockScaffold.getProperties(), scaffold.getProperties());
    }

    public static void assertProperties(List<Property> mockProperties, List<Property> properties) {
        assertEquals("Scaffold does not have correct number of properties!", mockProperties.size(), properties.size());
        for (int i = 0; i < mockProperties.size(); i++) {
            Property mockProperty = mockProperties.get(i);
            Property property = properties.get(i);
            assertProperty(mockProperty, property);
        }
    }

    public static void assertProperty(Property mockProperty, Property property) {
        assertEquals("Property does not have correct name!", mockProperty.getName(), property.getName());
        assertEquals("Property does not have correct class!", mockProperty.getClazz(), property.getClazz());
        assertEquals("Property does not have correct gloss!", mockProperty.getGloss(), property.getGloss());
        assertEquals("Property does not have correct help!", mockProperty.getHelp(), property.getHelp());
        assertEquals("Property does not have correct autocomplete!", mockProperty.getAutocomplete(), property.getAutocomplete());
        assertEquals("Property does not have correct autofocus flag!", mockProperty.isAutofocus(), property.isAutofocus());
        assertEquals("Property does not have correct hidden flag!", mockProperty.isHidden(), property.isHidden());
        assertEquals("Property does not have correct disable flag!", mockProperty.isDisabled(), property.isDisabled());
        assertOptions(mockProperty.getOptions(), property.getOptions());
        assertValidations(mockProperty.getValidations(), property.getValidations());
    }

    public static void assertOptions(List<Object> mockOptions, List<Object> options) {
        assertEquals("Property does not have correct number of options!", mockOptions.size(), options.size());
        for (int i = 0; i < mockOptions.size(); i++) {
            Object mockOption = mockOptions.get(i);
            Object option = options.get(i);
            assertEquals("Property does not have same option!", mockOption, option);
        }
    }

    public static void assertValidations(List<Validation> mockValidations, List<Validation> validations) {
        assertEquals("Property does not have correct number of validations!", mockValidations.size(), validations.size());
        for (int i = 0; i < mockValidations.size(); i++) {
            Validation mockValidation = mockValidations.get(i);
            Validation validation = validations.get(i);
            assertValidation(mockValidation, validation);
        }
    }

    public static void assertValidation(Validation mockValidation, Validation validation) {
        assertEquals("Validation does not have same name!", mockValidation.getName(), validation.getName());
        assertEquals("Validation does not have same value!", mockValidation.getValue(), validation.getValue());
        assertEquals("Validation does not have same message!", mockValidation.getMessage(), validation.getMessage());
    }

}
