package pl.sda.jira.project.model;

import java.util.List;

public interface ProjectRepository {

    void addProject(Project project);

    void removedProject(Long Id);

    List<Project> getAllProjects ();

    boolean checkIfTeamHasProject(Long teamId);


}
