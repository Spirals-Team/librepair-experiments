package pl.sda.jira.project.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepositoryInmemory implements ProjectRepository {

    Map<Long, Project> projects = new HashMap<>();

    @Override
    public Project get(Long id) {
            return projects.get(id);
    }

    @Override
    public boolean isExist(long projectId) {
        return projects.containsKey(projectId);
    }
}