package pl.sda.jira.project.domain;

import org.junit.Test;
import pl.sda.jira.project.model.*;

public class ProjectServiceTest {

    @Test(expected = ProjectDoesntExistException.class)
    public void shouldThrowExceptionWhenProjectDoesntExist() throws Exception {

        ProjectService service = new ProjectService(new ProjectRepositoryInmemory());
        long projectId = 1L;
        service.get(projectId);


    }
}
