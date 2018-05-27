package com.may.apimanagementsystem.user.service;

import com.may.apimanagementsystem.dao.ProjectMapper;
import com.may.apimanagementsystem.dao.UserProjectMapper;
import com.may.apimanagementsystem.po.Project;
import com.may.apimanagementsystem.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private UserProjectMapper userProjectMapper;

    @InjectMocks
    private ProjectService projectService = new ProjectService();

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getList() throws Exception {
        List<Project> projectList = new ArrayList<>();
        Project project = new Project();
        project.setProjectName("serviceTest");
        project.setAddress("56@dsa.com");
        project.setDescription("test123");
        project.setProjectId(9);
        project.setDelFlag(0);
        projectList.add(project);
        doReturn(projectList).when(projectMapper).getProjectList(anyInt());

        List<Project> projectList1 = projectService.getList(1,1,1000);

        assertEquals(projectList1,projectList);
    }

    @Test
    public void addProject() throws Exception {
        Project project = new Project();
        project.setProjectId(9);
        project.setProjectName("TestProject");
        project.setAddress("www.test.com");
        project.setDescription("This is a test");
        project.setDelFlag(0);
        doReturn(true).when(projectMapper).insertProject(any(Project.class));
        Boolean res = projectService.addProject(project);
        assertTrue(res);
    }

   @Test
    public void updateProject() throws Exception{
        Project project = new Project();
        project.setProjectId(667);
        project.setProjectName("serviceTest2");
        project.setAddress("42@dwq.com");
        project.setDescription("wew");
        project.setDelFlag(0);
        doReturn(true).when(projectMapper).updateProject(any(Project.class));

        Project project1 = project;
        project1.setProjectName("updateTest");
        Boolean res = projectService.updateProject(project1);
        assertTrue(res);
    }

    @Test
    public void removeProject() throws Exception{
        Project project = new Project();
        project.setProjectId(9);
        project.setProjectName("serviceTest3");
        project.setAddress("42r@dwq.com");
        project.setDescription("wewhj");
        project.setDelFlag(0);
        doReturn(true).when(projectMapper).deleteProject(anyInt());
        doReturn(true).when(userProjectMapper).deleteUserProject(anyInt());

        Project project1 = project;
        project1.setDelFlag(1);
        Boolean res = projectService.removeProject(9);
        assertTrue(res);
    }

    @Test
    public void getProjectByProjectId() throws Exception{
        Project project = new Project();
        project.setProjectId(9);
        project.setProjectName("TestProject");
        project.setAddress("www.test.com");
        project.setDescription("This is a test");
        project.setDelFlag(0);
        doReturn(project).when(projectMapper).findProjectByProjectId(anyInt());
        Project project1 = projectService.getProjectByProjectId(9);
        assertEquals(project,project1);
    }


}
