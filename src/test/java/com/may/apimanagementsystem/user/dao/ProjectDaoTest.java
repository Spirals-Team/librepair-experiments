package com.may.apimanagementsystem.user.dao;

import com.may.apimanagementsystem.dao.ProjectMapper;
import com.may.apimanagementsystem.po.Project;
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
public class ProjectDaoTest {

    @Autowired
    private ProjectMapper projectMapper;
    private Project project;

    @Before
    public void before(){
        project = new Project();
        project.setProjectName("TestProject");
        project.setProjectId(9);
        project.setDescription("This is a test");
        project.setAddress("www.test.com");
    }

    @Test
    public void getUserProjectTest(){
        List<Project> project = projectMapper.getProjectList(1000);
        assertEquals(0, project.size());
    }

    @Test
    public void deleteProjectTest(){
        boolean flag = projectMapper.deleteProject(1000);
        assertEquals(false, flag);
    }

    @Test
    public void insertProjectTest(){
        Project project1 = new Project();
        project1.setProjectName("TestProject233");
        project1.setProjectId(66);
        project1.setDescription("This is a test");
        project1.setAddress("www.test.com");
        boolean flag = projectMapper.insertProject(project1);
        assertEquals(true, flag);
    }

    @Test
    public void updateProjectTest(){
        boolean flag = projectMapper.updateProject(project);
        assertEquals(true, flag);
    }

    @Test
    public void findProjectByProjectIdTest(){
        Project project = projectMapper.findProjectByProjectId(9);
        assertEquals("TestProject", project.getProjectName());
    }
}
