package pl.sda.jira.project.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sda.jira.project.model.*;
import pl.sda.jira.project.repository.ProjectRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProjectServiceTest {


    @Autowired
    private ProjectRepository repository;
    private ProjectService service;

    @Before
    public void setUp() throws Exception {
        service = new ProjectService(repository);
    }

    @Test(expected = ProjectDoesntExistException.class)
    public void shouldThrowExceptionWhenProjectDoesNotExist() throws Exception {
        long identifier=1L;
        service.get(identifier);
    }

    @Test
    public void shouldReturnProjectWhenProjectExists() throws Exception {
        String name="abrakadabra";
        ProjectDto projectDto = new ProjectDto(name);
        Long identifier = service.add(projectDto);
        ProjectDto result = service.get(identifier);
        Assert.assertEquals(projectDto, result);
    }

    @Test(expected = ProjectAlreadyExistsException.class)
    public void shouldThrowExceptionIfProjectAlreadyExists() throws Exception {
        String name="thor";
        ProjectDto projectDto = new ProjectDto(name);
        service.add(projectDto);
        service.add(projectDto);
    }

    @Test(expected = ProjectDoesntExistException.class)
    public void shouldThrowExceptionIfWhenDeletedProjectIsNotExist() throws Exception {
        long identifier = 1L;
        service.delete(identifier);
    }
    @Test
    public void shouldDeleteProject() {
        String name="tony";
        long identifier = givenProject(name);
        service.delete(identifier);
        Assert.assertFalse(repository.isExist(identifier));
    }

    @Test
    public void shouldBeAbleToUpdateProject() throws Exception {
        String name="bruce";
        String newName="hulk";
        long id = givenProject(name);
        ProjectDto change = new ProjectDto(newName);
        service.update(id, change);
        ProjectDto changedProjectDto = service.get(id);
        Assert.assertEquals(newName,changedProjectDto.getName());
    }
    private long givenProject(String name) {
        ProjectDto projectDto = new ProjectDto(name);
        return service.add(projectDto);
    }
}