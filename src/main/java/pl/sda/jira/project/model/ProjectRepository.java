package pl.sda.jira.project.model;


public interface ProjectRepository {

    Project get(Long id);

    boolean isExist(long projectId);

    boolean isExist(String name);

    void add(Project project);

    void delete(long projectId);

    void update(Project project);
}
