package com.epam.brest.course.dao;

import com.epam.brest.course.model.Team;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-context-test.xml",
        "classpath:test-db.xml", "classpath:dao-context.xml"})
@Rollback
public class TeamDaoImplTest {

    private static final String TEAM_NAME = "team name";
    private static final String TEAM_COUNTRY = "team country";

    @Autowired
    TeamDao teamDao;

    @Test
    public void getAllTeamsTest(){

        Collection<Team> teams = teamDao.getAllTeams();
        Assert.assertTrue(((Integer) teams.size()).equals(2));
    }

    @Test
    public void addTeamTest(){

        Team team = new Team(3, TEAM_NAME, TEAM_COUNTRY);
        Team team1 = teamDao.addTeam(team);
        Assert.assertTrue(team1.getTeam_country()
                .equals(team.getTeam_country()));
        Assert.assertTrue(team1.getTeam_name().equals(team.getTeam_name()));
    }

    @Test
    public void deleteTeamTest(){

        Integer teamsCount = teamDao.getAllTeams().size();
        teamDao.deleteTeamById(1);
        Assert.assertTrue(((Integer) teamDao.getAllTeams()
                .size()).equals(teamsCount - 1));
    }

    @Test
    public void updateTeamTest(){

        Team team = new Team(3, TEAM_NAME, TEAM_COUNTRY);
        teamDao.addTeam(team);
        team.setTeam_name(TEAM_NAME + TEAM_NAME);
        team.setTeam_country(TEAM_COUNTRY + TEAM_COUNTRY);
        Team team1 = teamDao.updateTeam(team);
        Assert.assertTrue(team1.getTeam_name().equals(team.getTeam_name()));
        Assert.assertTrue(team1.getTeam_country()
                .equals(team.getTeam_country()));
    }

    @Test
    public void getTeamByIdTest(){

        Team team = teamDao.getTeamById(1);
        Assert.assertTrue(team.getTeam_name().equals("team 1"));
    }
}
