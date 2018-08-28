package com.roldukhine.jdbc;

import com.roldukhine.api.DepartmentDao;
import com.roldukhine.entity.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Repository
@Lazy
public class DepartmentDaoJdbcImpl extends AbstractDaoJdbcImpl<Department> implements DepartmentDao {

    private static final String TABLE_NAME = "Department";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET name = ?, manager_id = ? WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (name, manager_id) VALUES (?, ?)";

    @Override
    protected String getTableName() {
        logger.trace("getTableName");
        logger.debug("return TableName: " + TABLE_NAME);
        return TABLE_NAME;
    }

    @Override
    public void insert(final Department department) {
        logger.trace("insert: Department " + department);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            logger.debug("department: {}", department);
            prepareStatement.setObject(1, department.getName());
            prepareStatement.setObject(2, department.getManager() != null ? department.getManager().getId() : null);
            return prepareStatement;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        logger.debug("generated id {}", id);
        department.setId(id);
    }

    @Override
    public void update(Department department) {
        logger.debug("update with department{}", department);
        this.jdbcTemplate.update(UPDATE_SQL,
                department.getName(),
                department.getManager() != null ? department.getManager().getId() : null);
    }

    @Override
    protected Department createInstanceFromResult(ResultSet resultSet) throws SQLException {
        logger.debug("createInstanceFromResult: resultSet {}", resultSet);
        Department department = new Department();
        long id = resultSet.getLong("id");
        department.setId(id);
        String name = resultSet.getString("name");
        department.setName(name);

        return department;
    }
}
