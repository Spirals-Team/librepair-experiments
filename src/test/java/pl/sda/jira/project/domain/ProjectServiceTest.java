package pl.sda.jira.project.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.sda.jira.project.model.*;

public class ProjectServiceTest {

    private ProjectService service;
    private ProjectRepositoryInMemory repository;
    private static final String NAME = "peter";
    private static final String NEW_NAME = "spider-man";
    private static final Long ID = 1L;

    @Before
    public void setUp() throws Exception {
        repository = new ProjectRepositoryInMemory();
        service = new ProjectService(repository);
    }

    @Test(expected = ProjectDoesntExistException.class)
    public void shouldThrowExceptionWhenProjectDoesNotExist() throws Exception {
        service.get(ID);
    }

    @Test
    public void shouldReturnProjectWhenProjectExists() throws Exception {
        ProjectDto projectDto = new ProjectDto(NAME);
        Long identifier = service.add(projectDto);
        ProjectDto result = service.get(identifier);
        Assert.assertEquals(projectDto, result);
    }

    @Test(expected = ProjectAlreadyExistsException.class)
    public void shouldThrowExceptionIfProjectAlreadyExists() throws Exception {
        ProjectDto projectDto = new ProjectDto(NAME);
        service.add(projectDto);
        service.add(projectDto);
    }

    @Test(expected = ProjectDoesntExistException.class)
    public void shouldThrowExceptionIfWhenDeletedProjectIsNotExist() throws Exception {
        long wrongId = 1L;
        service.delete(wrongId);
    }
    @Test
    public void shouldDeleteProject() {
        ProjectDto projectDto = new ProjectDto(NAME);
        long id = service.add(projectDto);
        service.delete(id);
        Assert.assertFalse(repository.isExist(id));
    }

    @Test
    public void shouldBeAbleToUpdateProject() throws Exception {
        long id = service.add(new ProjectDto(NAME));
        ProjectDto change = new ProjectDto(NEW_NAME);
        service.update(id, change);
        ProjectDto changedProjectDto = service.get(id);
        Assert.assertEquals(NEW_NAME,changedProjectDto.getName());
    }
}