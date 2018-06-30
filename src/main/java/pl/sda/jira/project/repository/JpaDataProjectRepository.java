package pl.sda.jira.project.repository;

import org.springframework.stereotype.Repository;
import pl.sda.jira.project.model.Project;

import java.util.Optional;

@Repository
public class JpaDataProjectRepository implements ProjectRepository {
    private final CrudJpaProjectRepository repository;

    public JpaDataProjectRepository(CrudJpaProjectRepository repository) {

        this.repository = repository;
    }

    @Override
    public Project get(Long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean isExist(long id) {
        return repository.exists(id);
    }

    @Override
    public boolean isExist(String name) {
        Optional<Project> optionalProject = repository.getProjectByName(name);
        return optionalProject.isPresent();
    }

    @Override
    public void add(Project project) {
        repository.save(project);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    public void update(Project project) {
        repository.save(project);
    }
}
