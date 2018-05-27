package com.may.apimanagementsystem.user.dao;


import com.may.apimanagementsystem.dao.TeamMemberMapper;
import com.may.apimanagementsystem.po.TeamMember;
import com.may.apimanagementsystem.po.User;
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
public class TeamMemberDaoTest {

    @Autowired
    private TeamMemberMapper teamMemberMapper;
    private TeamMember teamMember;

    @Before
    public void before()
    {
        teamMember=new TeamMember();
        teamMember.setTeamId(1001);
        teamMember.setUserId(1001);
    }

    @Test
    public void insertTeamMemberTest()
    {

        boolean flag=teamMemberMapper.insertTeamMember(teamMember);
        assertEquals(true, flag);

    }

    @Test
    public void deleteTeamMemberTest(){
        boolean flag=teamMemberMapper.deleteTeamMember(teamMember);
        assertEquals(true, flag);
    }

   @Test
    public void selectTeamMemberTest()
   {
       List<User> users=teamMemberMapper.selectTeamMember(1001);
       System.out.println(users.get(1).getUserName());
       assertEquals(2,users.size());
   }

}
