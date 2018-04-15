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
}
