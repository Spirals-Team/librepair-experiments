package pl.sda.jira.project.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.sda.jira.project.model.ProjectDoesntExistException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProjectAlreadyExistsException.class)
    public ResponseEntity<String> projectAlreadyExists(ProjectAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ProjectDoesntExistException.class)
    public ResponseEntity<String>notFound(ProjectDoesntExistException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
}
