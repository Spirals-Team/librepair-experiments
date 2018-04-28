package pl.sda.jira.project;

import pl.sda.jira.project.model.ProjectRepository;
import pl.sda.jira.project.model.TeamRepository;

public class ProjectControler {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    public ProjectControler(TeamRepository teamRepository, ProjectRepository projectRepository) {
        this.teamRepository = teamRepository;
        this.projectRepository=projectRepository;
    }


    public boolean checkIfTeamExists(Long teamId) {
        if (teamId != null) {
            return teamRepository.checkIfTeamExist(teamId);
        } else return false;
    }

    public boolean checkIfProjectAssignetToTeam(Long teamId){
        if (teamId != null){
            return projectRepository.checkIfTeamHasProject(teamId);
        }else return false;
    }
}
