package pl.sda.jira.project.model;

import java.util.*;

public class FakeProjectRepository implements ProjectRepository {

    private Map<Long, Project> projects = new HashMap<>();


    @Override
    public void addProject(Project project) {
        projects.put(project.getId(), project);
    }

    @Override
    public void removedProject(Long id) {
        projects.remove(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return new ArrayList<Project>(projects.values());
    }

    @Override
    public boolean checkIfTeamHasProject(Long teamid) {
        for (Project project : projects.values()){
            if(project.getTeam().getId() == teamid){
                return true;
            }
        }
        return false;
    }


}
