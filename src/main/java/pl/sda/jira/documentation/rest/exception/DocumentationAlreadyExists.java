package pl.sda.jira.documentation.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DocumentationAlreadyExists extends RuntimeException {
    public DocumentationAlreadyExists(String name) {
        super("Documentation: " + name + " already exists.");
    }
}
