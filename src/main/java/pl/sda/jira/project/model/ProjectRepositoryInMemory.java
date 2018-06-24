package pl.sda.jira.project.model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepositoryInMemory implements ProjectRepository {

    private Map<Long, Project> projects = new HashMap<>();

    @Override
    public Project get(Long id) {
        return projects.get(id);
    }

    @Override
    public boolean isExist(long projectId) {
        return projects.containsKey(projectId);
    }

    @Override
    public boolean isExist(String name) {
        List<Project> list = new ArrayList<>(projects.values());
        for(Project project:list){
            if( project.getName().equals(name))
                return true;
        }return false;
    }

    @Override
    public void add(Project project) {
        projects.put(project.getId(),project);
    }

    @Override
    public void delete(long projectId) {
        projects.remove(projectId);
    }

    @Override
    public void update(Project project) {
        add(project);
    }
}


