package com.may.apimanagementsystem.user.dao;


import com.may.apimanagementsystem.dao.TeamMapper;
import com.may.apimanagementsystem.dao.TeamMemberMapper;
import com.may.apimanagementsystem.po.Team;
import com.may.apimanagementsystem.po.TeamMember;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class TeamDaoTest {

    @Autowired
    private TeamMapper teamMapper;
    private Team team;

    @Before
    public void before()
    {
        team=new Team();
        team.setTeamName("lalala");
        team.setDescription("252525");
        team.setCreateuserId(1003);
    }

    @Test
    public void insertTeamTest()
    {
        team.setTeamName("sdsdsd");
        boolean flag=teamMapper.insertTeam(team);
        assertEquals(true,flag);
    }

    @Test
    public void deleteTeamTest()
    {
        boolean flag=teamMapper.deleteTeam(1001);
        assertEquals(true, flag);
    }

    @Test
    public  void updateTeamTest()
    {   team.setTeamId(1002);
        boolean flag=teamMapper.updateTeam(team);
        assertEquals(true, flag);
    }

    @Test
    public void searchTeamsByuserIdTest()
    {
        List<Team> teams=teamMapper.searchMyTeamsByuserId(1000);
        System.out.println(teams.get(1).getTeamName());
        assertEquals(2,teams.size());
    }

    @Test
    public void findTeamByTeamid()
    {
        Team team=teamMapper.findTeamByTeamId(1002);
        assertEquals(1001,team.getCreateuserId());
    }

}
