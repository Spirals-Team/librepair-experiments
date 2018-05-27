package com.may.apimanagementsystem.user.dao;


import com.may.apimanagementsystem.dao.TeamProjectMapper;
import com.may.apimanagementsystem.po.Project;
import com.may.apimanagementsystem.po.TeamProject;
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
public class TeamProjectDaoTest {

    @Autowired
    private TeamProjectMapper teamProjectMapper;
    private TeamProject teamProject;



   @Test
    public void deleteTeamProjectTset()
   {
       boolean flag=teamProjectMapper.deleteTeamProject(1);
       assertEquals(true, flag);
   }

   @Test
    public void selectTeamproject()
   {
       List<Project> projects=teamProjectMapper.selectTeamProject(1001);
       System.out.println(projects.get(1).getProjectName());
       assertEquals(3,projects.size());
   }

}
