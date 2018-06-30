package pl.sda.jira.project.model;

        import org.springframework.stereotype.Service;
        import pl.sda.jira.project.domain.ProjectAlreadyExistsException;
        import pl.sda.jira.project.repository.ProjectRepository;

        import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public ProjectDto get(long projectId) {
        if (!repository.isExist(projectId)) {
            throw new ProjectDoesntExistException();
        } else {
            return repository.get(projectId).asDto();
        }
    }

    public long add(ProjectDto projectDto) {
        if (repository.isExist(projectDto.getName())) throw new ProjectAlreadyExistsException(projectDto.getName());
        else {

            Project project = new Project(projectDto);
            repository.add(project);
            return project.getId();
        }
    }

    public void delete(long projectId) {
        if (repository.isExist(projectId)) {
            repository.delete(projectId);
        } else {
            throw new ProjectDoesntExistException();
        }
    }

    public void update(Long id, ProjectDto projectDto) {
        if (repository.isExist(id)) {
            Project project = repository.get(id);
            project.update(projectDto);
            repository.add(project);
        } else throw new ProjectDoesntExistException();
    }
}

