package com.roldukhine.jdbc;

import com.roldukhine.api.UserDao;
import com.roldukhine.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Repository
public class UserDaoJdbcImpl extends AbstractDaoJdbcImpl<User> implements UserDao {

    private static final String TABLE_NAME = "User";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (login, password) VALUES (?, ?)";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET login = ?, password = ? WHERE id = ?";

    @Override
    protected String getTableName() {
        logger.trace("getTableName");
        logger.debug("return TableName: " + TABLE_NAME);
        return TABLE_NAME;
    }

    @Override
    public void update(User entity) {
        logger.debug("update entity {}", entity);
        this.jdbcTemplate.update(UPDATE_SQL,
                entity.getLogin(),
                entity.getPassword(),
                entity.getId());
    }

    @Override
    public void insert(final User entity) {
        logger.debug("insert user {}", entity);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setObject(1, entity.getLogin());
            prepareStatement.setObject(2, entity.getPassword());
            return prepareStatement;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        logger.debug("return new id {}", id);
        entity.setId(id);
    }

    @Override
    protected User createInstanceFromResult(ResultSet resultSet) throws SQLException {
        logger.debug("createInstanceFromResult: resultSet {}", resultSet);
        User user = new User();
        long id = resultSet.getLong("id");
        logger.debug("id from resultSet {}", id);
        user.setId(id);
        String login = resultSet.getString("login");
        logger.debug("login from resultSet {}", login);
        user.setLogin(login);
        String password = resultSet.getString("password");
        logger.debug("password from resultSet {}", password);
        user.setPassword(password);
        return user;
    }
}
