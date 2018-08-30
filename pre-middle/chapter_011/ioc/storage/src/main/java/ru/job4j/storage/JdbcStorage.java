package ru.job4j.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.job4j.model.User;
import ru.job4j.transformer.UserRowMapper;

import java.util.Collection;

/**
 * Class JdbcStorage.
 * Implement storage with database. Entity to save is {@link User}
 */
public class JdbcStorage implements Storage<User, Long> {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public JdbcStorage setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    @Override
    public User findOne(final Long key) {
        return this.jdbcTemplate.query(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{key},
            new UserRowMapper()).get(0);
    }

    @Override
    public Collection<User> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM users",
            new UserRowMapper());
    }

    @Override
    public Long create(final User entity) {
        this.jdbcTemplate.update("INSERT INTO users (id, email, passw) VALUES(?,?,?)",
            entity.getId(), entity.getEmail(), entity.getPassw());
        return entity.getId();
    }

    @Override
    public void update(final User entity) {
        this.jdbcTemplate.update("UPDATE users SET email = ?, passw =? WHERE id = ?",
            entity.getEmail(), entity.getPassw(), entity.getId());
    }

    @Override
    public void delete(final User entity) {
        this.deleteById(entity.getId());
    }

    @Override
    public void deleteById(final Long key) {
        this.jdbcTemplate.update("DELETE FROM users where id = ?",
            key);
    }
}
