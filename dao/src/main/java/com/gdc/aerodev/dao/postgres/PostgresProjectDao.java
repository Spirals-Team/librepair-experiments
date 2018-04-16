package com.gdc.aerodev.dao.postgres;

import com.gdc.aerodev.dao.ProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
public class PostgresProjectDao extends AbstractDao<Project, Long> implements ProjectDao {

    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private final String SELECT_QUERY = "SELECT prj_id, prj_name, prj_owner, prj_type, prj_description FROM ";

    @Autowired
    public PostgresProjectDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = getTableName("project.table");
    }

    public PostgresProjectDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    public List<Project> getByUserId(Long usrId){
            return jdbcTemplate.query(SELECT_QUERY + tableName + " WHERE prj_owner = ?;",
                    new ProjectRowMapper(), usrId);
    }

    public List<Project> getTopThree() {
        return jdbcTemplate.query(SELECT_QUERY + tableName + " LIMIT 3;", new ProjectRowMapper());
        //TODO: add rating logic
    }

    @Override
    public Project getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE prj_id = ?;",
                    new ProjectRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Project getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + tableName + " WHERE prj_name = ?;",
                    new ProjectRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Project> getAll() {
        return jdbcTemplate.query(SELECT_QUERY + tableName + ";",
                new ProjectRowMapper());
    }

    protected Long insert(Project entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (prj_name, prj_owner, prj_type, prj_description) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"prj_id"});
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
                        " SET prj_name=?, prj_owner=?, prj_type=?, prj_description=? WHERE prj_id = "
                        + entity.getProjectId() + ";",
                entity.getProjectName(),
                entity.getProjectOwner(),
                entity.getProjectType().toString(),
                entity.getProjectDescription());
        return (rows > 0) ? entity.getProjectId() : null;
    }

    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE prj_id = ?;", id);
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

    private static class ProjectRowMapper implements RowMapper<Project>{
        /**
         * Utility method, which builds {@code Project} entity from inserted {@code ResultSet}
         * @param resultSet incoming {@code ResultSet}
         * @return built {@code Project} entity
         * @throws SQLException if build was performed incorrectly (see stacktrace)
         */
        @Override
        public Project mapRow(ResultSet resultSet, int i) throws SQLException {
            Project project = new Project();
            project.setProjectId(resultSet.getLong("prj_id"));
            project.setProjectName(resultSet.getString("prj_name"));
            project.setProjectOwner(resultSet.getLong("prj_owner"));
            project.setProjectType(ProjectType.valueOf(resultSet.getString("prj_type").toUpperCase()));
            project.setProjectDescription(resultSet.getString("prj_description"));
            return project;
        }
    }
}
