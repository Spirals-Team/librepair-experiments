package pl.sda.jira.project.model;


public class ProjectDoesntExistException extends RuntimeException{
    public ProjectDoesntExistException() {
        super("not found");
    }
}
