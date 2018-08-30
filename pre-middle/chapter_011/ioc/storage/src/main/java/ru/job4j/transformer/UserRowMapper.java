package ru.job4j.transformer;

import org.springframework.jdbc.core.RowMapper;
import ru.job4j.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class UserRowMapper.
 * Transform {@link ResultSet} to object {@link User}
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(final ResultSet rs, final int i) throws SQLException {
        return new User()
            .setId(rs.getLong(1))
            .setEmail(rs.getString(2))
            .setPassw(rs.getString(3));
    }
}
