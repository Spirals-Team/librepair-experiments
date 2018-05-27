package pl.sda.jira.project.model;

public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project get(long projectId) {
        if(repository.isExist(projectId)){
            return repository.get(projectId);
        } else {
            throw new ProjectDoesntExistException();
        }
    }
}
