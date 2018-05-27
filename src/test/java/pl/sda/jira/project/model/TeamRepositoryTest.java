package pl.sda.jira.project.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/jira-sda-app.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TeamRepositoryTest {
    @Autowired TeamRepository teamRepository;

    @Test
    public void schouldTestAddingToTeamRepository(){
        Team teamA = new Team();
        teamA.setTeamName("Team A");
        teamA.setId(1L);

        Team teamB = new Team();
        teamB.setTeamName("Team B");
        teamB.setId(2L);

        teamRepository.addTeam(teamA);
        assertTrue(teamRepository.checkIfTeamExist(teamA.getId()));
        assertFalse(teamRepository.checkIfTeamExist(teamB.getId()));

        teamRepository.addTeam(teamB);
        assertTrue(teamRepository.checkIfTeamExist(teamA.getId()));
        assertTrue(teamRepository.checkIfTeamExist(teamB.getId()));
    }

    @Test
    public void schouldTestRemoveToTeamRepository(){
        Team teamA = new Team();
        teamA.setTeamName("Team A");
        teamA.setId(1L);

        Team teamB = new Team();
        teamB.setTeamName("Team B");
        teamB.setId(2L);

        teamRepository.addTeam(teamA);
        teamRepository.addTeam(teamB);
        assertTrue(teamRepository.checkIfTeamExist(teamA.getId()));
        assertTrue(teamRepository.checkIfTeamExist(teamB.getId()));

        teamRepository.removeTeam(teamA.getId());
        assertFalse(teamRepository.checkIfTeamExist(teamA.getId()));
        assertTrue(teamRepository.checkIfTeamExist(teamB.getId()));

        teamRepository.removeTeam(teamB.getId());
        assertFalse(teamRepository.checkIfTeamExist(teamA.getId()));
        assertFalse(teamRepository.checkIfTeamExist(teamB.getId()));
    }

}