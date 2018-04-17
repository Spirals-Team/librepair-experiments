package com.gdc.aerodev.dao.test;

import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresProjectDaoTest {

    private String tableName = "project_test";
    private String name = "start-up";
    private Long owner = 1L;
    private ProjectType type = ProjectType.AERODYNAMICS;
    private String description = "This is a new project of...";
    private Project project = new Project(name, owner, type, description);

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project"));

    //Standard tests

    @Test
    public void testGetById() {
        PostgresProjectDao dao = getDao();
        Project project = dao.getById(1L);
        assertEquals("3D test", project.getProjectName());
        assertEquals(ProjectType.DESIGN, project.getProjectType());
        assertEquals(Long.valueOf(1), project.getProjectOwner());
    }

    @Test
    public void testGetByName() {
        PostgresProjectDao dao = getDao();
        Project project = dao.getByName("3D test");
        assertEquals(ProjectType.DESIGN, project.getProjectType());
        assertEquals(Long.valueOf(1), project.getProjectOwner());
    }

    @Test
    public void testGetAll() {
        PostgresProjectDao dao = getDao();
        List<Project> list = dao.getAll();
        assertEquals(3, list.size());
    }

    @Test
    public void testInsert() {
        PostgresProjectDao dao = getDao();
        Long id = dao.save(project);
        assertEquals(name, dao.getById(id).getProjectName());
    }

    @Test
    public void testUpdate() {
        PostgresProjectDao dao = getDao();
        Long id = 1L;
        String name = "Thing";
        ProjectType type = ProjectType.AERODYNAMICS;
        Project project = dao.getById(id);
        assertEquals("3D test", project.getProjectName());
        project.setProjectName(name);
        project.setProjectType(type);
        dao.save(project);
        assertEquals(name, dao.getById(id).getProjectName());
        assertEquals(type, dao.getById(id).getProjectType());
    }

    @Test
    public void testDelete() {
        PostgresProjectDao dao = getDao();
        int size = dao.getAll().size();
        dao.delete(2L);
        assertEquals(size - 1, dao.getAll().size());
    }

    @Test
    public void testCount(){
        PostgresProjectDao dao = getDao();
        assertEquals(3, dao.count());
    }

    //Abnormal tests

    @Test
    public void testGetByIdNonExistent() {
        PostgresProjectDao dao = getDao();
        assertNull(dao.getById(-1L));
    }

    @Test
    public void testGetByNameNonExistent() {
        PostgresProjectDao dao = getDao();
        assertNull(dao.getByName("!!!"));
    }

    @Test(expected = DaoException.class)
    public void testInsertExistentProjectException() {
        PostgresProjectDao dao = getDao();
        String newName = "3D test";
        project.setProjectName(newName);
        assertNull(dao.save(project));
    }

    @Test
    public void testInsertExistentUserDbSize() {
        PostgresProjectDao dao = getDao();
        String newName = "3D test";
        project.setProjectName(newName);
        int size = dao.getAll().size();
        try {
            assertNull(dao.save(project));
        } catch (DaoException e) {
            assertEquals(size, dao.getAll().size());
        }
    }

    @Test
    public void testDeleteNonExistentUser(){
        PostgresProjectDao dao = getDao();
        assertFalse(dao.delete(-1L));
    }

    private PostgresProjectDao getDao() {
        return new PostgresProjectDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }
}
