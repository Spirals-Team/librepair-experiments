package com.epam.brest.course.service;

import com.epam.brest.course.model.Team;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml",
        "classpath:test-db.xml", "classpath:dao-context.xml"})
@Rollback
public class TeamServiceImplTest {

    @Autowired
    private TeamService teamService;

    @Test
    public void addTeamTest(){

        Team team = new Team(3, "test name", "test country");
        Integer teamCounter = teamService.getAllTeams().size();
        teamService.addTeam(team);
        Assert.assertTrue(((Integer) teamService.getAllTeams().size())
                .equals(teamCounter + 1));
    }

    @Test
    public void getAllTeamsTest(){

        Assert.assertTrue(((Integer) teamService
                .getAllTeams().size()).equals(2));
    }

    @Test
    public void deleteTeamTest(){

        Integer teamCounter = teamService.getAllTeams().size();
        teamService.deleteTeamById(1);
        Assert.assertTrue(((Integer) teamService.getAllTeams().size())
                .equals(teamCounter - 1));
    }

    @Test
    public void getTeamByIdTest(){

        Team team = teamService.getTeamById(1);
        Assert.assertTrue(team.getTeam_id().equals(1));
        Assert.assertTrue(team.getTeam_name().equals("team 1"));
        Assert.assertTrue(team.getTeam_country().equals("country 1"));
    }

    @Test
    public void updateTeamTest(){

        Team team = new Team(1,"test","test");
        teamService.updateTeam(team);
        Team team1 = teamService.getTeamById(1);
        Assert.assertTrue(team1.getTeam_id().equals(1));
        Assert.assertTrue(team1.getTeam_name().equals("test"));
        Assert.assertTrue(team1.getTeam_country().equals("test"));
    }
}
