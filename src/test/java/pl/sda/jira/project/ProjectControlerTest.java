package pl.sda.jira.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sda.jira.project.model.Project;
import pl.sda.jira.project.model.ProjectRepository;
import pl.sda.jira.project.model.Team;
import pl.sda.jira.project.model.TeamRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/jira-sda-app.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectControlerTest {

    @Autowired private ProjectControler projectControler;
    @Autowired private TeamRepository andrzeje;
    @Autowired private ProjectRepository projectRepository;

    @Test
    public void shouldReturnTrueIfTeamHasGotAProject(){
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("the best teeam");
        Project project = new Project();
        project.setId(2L);
        project.setTeam(team);

        projectRepository.addProject(project);

        assertTrue(projectControler.checkIfProjectAssignetToTeam(team.getId()));
    }

    @Test
    public void shouldReturnFalseIfTeamHasntGotProject()  {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("the best teeam");
        Project project = new Project();
        project.setId(2L);

        projectRepository.addProject(project);
        assertFalse(projectControler.checkIfProjectAssignetToTeam(team.getId()));
    }
}