package pl.sda.jira.controler;

import pl.sda.jira.project.model.ProjectRepository;
import pl.sda.jira.project.model.TeamRepository;

public class ProjectControler {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    public ProjectControler(TeamRepository teamRepository, ProjectRepository projectRepository) {
        this.teamRepository = teamRepository;
        this.projectRepository=projectRepository;
    }


}
