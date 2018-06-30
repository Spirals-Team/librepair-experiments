package pl.sda.jira.documentation.domain.exception;

import org.junit.Test;
import pl.sda.jira.documentation.rest.exception.DocumentationAlreadyExists;

import static org.junit.Assert.assertEquals;

public class DocumentationAlreadyExistsExceptionTest {
    private static final String TITLE = "documentation1";
    @Test
    public void shouldShowAppropriateMessage() {

        DocumentationAlreadyExists exception = new DocumentationAlreadyExists(TITLE);
        assertEquals("Documentation: " + TITLE + " already exists.", exception.getMessage());
    }

}
