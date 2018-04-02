package com.gdc.aerodev.dao.specific;

import com.gdc.aerodev.dao.AbstractDao;
import com.gdc.aerodev.dao.GenericDao;
import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Realization of data access object for working with {@code User} instance
 *
 * @see User
 * @author Yusupov Danil
 */
@Repository
public class UserDao extends AbstractDao<User, Long> {

    private final JdbcTemplate jdbcTemplate;
    private String tableName;

    public UserDao(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }


    @Override
    public User getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE userId = ?;",
                    (rs, rowNum) -> {
                        return buildUser(rs);
                    }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public User getByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE userName = ?;",
                    (rs, rowNum) -> {
                        return buildUser(rs);
                    }, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + tableName + " ;",
                (rs, rowNum) -> {
                    return buildUser(rs);
                }
        );
    }

    protected Long insert(User entity) {
        final String INSERT_SQL = "INSERT INTO " + tableName + " (userName, userPassword, userEmail) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"userid"});
                    ps.setString(1, entity.getUserName());
                    ps.setString(2, entity.getUserPassword());
                    ps.setString(3, entity.getUserEmail());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    protected Long update(User entity) {
        int rows = jdbcTemplate.update("UPDATE " + tableName +
                        " SET userName=?, userPassword=?, userEmail=?, userLevel=? WHERE userId = "
                        + entity.getUserId() + ";",
                entity.getUserName(),
                entity.getUserPassword(),
                entity.getUserEmail(),
                entity.getUserLevel());
        return (rows > 0) ? entity.getUserId() : null;
    }


    @Override
    public boolean delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + tableName + " WHERE userId = ?;", id);
        return rows > 0;
    }

    @Override
    protected boolean isNew(User entity) {
        return entity.getUserId() == null;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName + ";", Integer.class);
    }

    /**
     * Checks inserted email on existence in database
     * @param userEmail email to check
     * @return (0) {@param userName} of {@code User} with existent email
     *         (1) {@code null} if there is no such email
     */
    public String existentEmail(String userEmail){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT userName FROM " + tableName + " WHERE userEmail = ?;",
                    new Object[]{userEmail},
                    String.class);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * Utility method, which builds {@code User} entity from inserted {@code ResultSet}
     * @param rs incoming {@code ResultSet}
     * @return built {@code User} entity
     * @throws SQLException if build was performed incorrectly (see stacktrace)
     */
    private User buildUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("userId"));
        user.setUserName(rs.getString("userName"));
        user.setUserPassword(rs.getString("userPassword"));
        user.setUserEmail(rs.getString("userEmail"));
        user.setUserLevel(rs.getShort("userLevel"));
        return user;
    }
}
