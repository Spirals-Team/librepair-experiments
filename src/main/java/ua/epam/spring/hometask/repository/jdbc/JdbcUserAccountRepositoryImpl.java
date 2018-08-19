package ua.epam.spring.hometask.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.repository.UserAccountRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;

@Repository
public class JdbcUserAccountRepositoryImpl implements UserAccountRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserAccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean buy(UserAccount userAccount, double price) {
        String sql = "UPDATE user_accounts SET money=money-" + price + " WHERE id=? AND money>=" + price;
        return 1 == jdbcTemplate.update(sql, userAccount.getId());
    }

    @Override
    public UserAccount save(UserAccount userAccount, long userId) {
        userAccount.setUserId(userId);
        int result;
        if (userAccount.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            result = jdbcTemplate.update(con -> {
                String sql = "INSERT INTO user_accounts (user_id, name, money) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userAccount.getUserId());
                ps.setString(2, userAccount.getName());
                ps.setDouble(3, userAccount.getMoney());
                return ps;
            }, keyHolder);
            userAccount.setId(keyHolder.getKey().longValue());
        } else {
            String sql = "UPDATE user_accounts SET user_id=?, name=?, money=? WHERE id=?";
            result = jdbcTemplate.update(sql,
                    userAccount.getUserId(),
                    userAccount.getName(),
                    userAccount.getMoney(),
                    userAccount.getId());
        }

        return result == 1 ? userAccount : null;
    }

    @Override
    public boolean delete(UserAccount userAccount, long userId) {
        Long id = userAccount.getId();
        Objects.requireNonNull(id);
        return jdbcTemplate.update("DELETE FROM user_accounts WHERE id=? and user_id=?", id, userId) != 0;
    }

    @Override
    public UserAccount get(long id, long userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM user_accounts WHERE id=? and user_id=?",
                new Object[]{id, userId},
                UserAccountRowMapper());
    }

    @Override
    public UserAccount getByName(String name, long userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM user_accounts WHERE name=? and user_id=?",
                new Object[]{name, userId},
                UserAccountRowMapper());
    }

    @Override
    public Collection<UserAccount> getAll(long userId) {
        return jdbcTemplate.query("SELECT * FROM user_accounts WHERE user_id=?",
                new Object[]{userId},
                UserAccountRowMapper());
    }

    private RowMapper<UserAccount> UserAccountRowMapper() {
        return (rs, rowNum) -> {
            UserAccount userAccount = new UserAccount();
            userAccount.setId(rs.getLong("id"));
            userAccount.setUserId(rs.getLong("user_id"));
            userAccount.setName(rs.getString("name"));
            userAccount.setMoney(rs.getDouble("money"));
            return userAccount;
        };
    }
}
