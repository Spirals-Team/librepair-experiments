package com.gdc.aerodev.dao.specific;

import com.gdc.aerodev.dao.AbstractDao;
import com.gdc.aerodev.dao.GenericDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Realization of data access object for working with {@code Project} instance
 *
 * @see Project
 * @author Yusupov Danil
 */
@Repository
public class ProjectDao extends AbstractDao<Project, Long> {

    private final JdbcTemplate jdbcTemplate;
    private String tableName;

    public ProjectDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public Project getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE projectId = ?;",
                    (rs, rowNum) -> {
                        return buildProject(rs);
                    }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Project getByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE projectName = ?;",
                    (rs, rowNum) -> {
                        return buildProject(rs);
                    }, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Project> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + tableName + " ;",
                (rs, rowNum) -> {
                    return buildProject(rs);
                }
        );
    }

    protected Long insert(Project entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (projectName, projectOwner, projectType, projectDescription) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"projectid"});
                    ps.setString(1, entity.getProjectName());
                    ps.setLong(2, entity.getProjectOwner());
                    ps.setString(3, entity.getProjectType().toString());
                    ps.setString(4, entity.getProjectDescription());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    protected Long update(Project entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName +
                        " SET projectName=?, projectOwner=?, projectType=?, projectDescription=? WHERE projectId = "
                        + entity.getProjectId() + ";",
                entity.getProjectName(),
                entity.getProjectOwner(),
                entity.getProjectType().toString(),
                entity.getProjectDescription());
        return (rows > 0) ? entity.getProjectId() : null;
    }

    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE projectId = ?;", id);
        return rows > 0;
    }

    @Override
    protected boolean isNew(Project entity) {
        return entity.getProjectId() == null;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName + ";", Integer.class);
    }

    private Project buildProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getLong("projectId"));
        project.setProjectName(rs.getString("projectName"));
        project.setProjectOwner(rs.getLong("projectOwner"));
        project.setProjectType(ProjectType.valueOf(rs.getString("projectType")));
        project.setProjectDescription(rs.getString("projectDescription"));
        return project;
    }
}
