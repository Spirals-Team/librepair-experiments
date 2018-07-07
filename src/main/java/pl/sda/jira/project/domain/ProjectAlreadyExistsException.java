package pl.sda.jira.project.domain;

public class ProjectAlreadyExistsException extends RuntimeException{
    public ProjectAlreadyExistsException(String name) {
        super(name+" already exists.");
    }
}
