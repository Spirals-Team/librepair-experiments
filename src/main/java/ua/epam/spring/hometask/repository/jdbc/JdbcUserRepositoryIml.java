package ua.epam.spring.hometask.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Role;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.to.UserLuckyDate;
import ua.epam.spring.hometask.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Vladimir on 28.10.2017.
 */
@Repository
public class JdbcUserRepositoryIml implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired//we can avoid it because of Spring 4.3
    public JdbcUserRepositoryIml(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        int result;
        Timestamp birthday = Timestamp.valueOf(LocalDateTime.of(user.getBirthday(), LocalTime.ofSecondOfDay(0)));
        String roles;
        if (user.getRoles() == null) {
            roles = Role.REGISTERED_USER.name();
        } else {
            roles = user.getRoles().stream().map(Enum::name).collect(Collectors.joining(","));
        }
        if (user.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            result = jdbcTemplate.update(con -> {
                String sql = "INSERT INTO users (first_name, last_name, email, birthday, password, roles) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setTimestamp(4, birthday);
                ps.setString(5, user.getPassword());
                ps.setString(6, roles);
                return ps;
            }, keyHolder);
            user.setId(keyHolder.getKey().longValue());

        } else if (user.getPassword() == null) {
            String sql = "UPDATE users SET first_name=?, last_name=?, email=?, birthday=?, roles=? WHERE id=?";
            result = jdbcTemplate.update(sql,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    birthday,
                    roles,
                    user.getId());

        } else {
            String sql = "UPDATE users SET first_name=?, last_name=?, email=?, birthday=?, password=?, roles=? WHERE id=?";
            result = jdbcTemplate.update(sql,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    birthday,
                    user.getPassword(),
                    roles,
                    user.getId());

        }

        if (user.getLuckyEvents() != null && user.getLuckyEvents().size() > 0) {
            jdbcTemplate.update("DELETE FROM user_lucky_dates WHERE user_id=?", user.getId());
            user.getLuckyEvents().forEach(luckyDate -> {

                jdbcTemplate.update("INSERT INTO user_lucky_dates(user_id, lucky_datetime) VALUES(?,?)",
                        user.getId(),
                        Timestamp.valueOf(luckyDate));
            });
        }

        return result == 1 ? user : null;

    }

    @Override
    public boolean delete(User user) {
        Long id = user.getId();
        Objects.requireNonNull(id);
        jdbcTemplate.update("DELETE FROM user_lucky_dates WHERE user_id=?", id);
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(long id) {
        User u;
        u = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new Object[]{id},
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setBirthday(rs.getTimestamp("birthday").toLocalDateTime().toLocalDate());
                    user.setPassword(rs.getString("password"));
                    user.setRoles(Stream.of(rs.getString("roles").split(",")).map(Role::valueOf).collect(Collectors.toSet()));
                    return user;
                });

        List<UserLuckyDate> luckyDates = jdbcTemplate.query(
                "SELECT * FROM user_lucky_dates WHERE user_id=?",
                new Object[]{id},
                (rs, rowNum) -> {
                    UserLuckyDate luckyDate = new UserLuckyDate(
                            rs.getLong("user_id"),
                            rs.getTimestamp("lucky_datetime").toLocalDateTime()
                    );
                    return luckyDate;
                });
        if (!luckyDates.isEmpty()) {
            u.setLuckyEvents(luckyDates.stream().map(UserLuckyDate::getLuckyDate).collect(Collectors.toSet()));
        }

        return u;
    }

    @Override
    public Collection<User> getAll() {

        return jdbcTemplate.query(
                "SELECT u.id, u.first_name, u.last_name, u.email, u.birthday, u.password, u.roles, l.lucky_datetime" +
                        " FROM users u LEFT JOIN user_lucky_dates l ON u.id=l.user_id",
                rs -> {
                    Map<User, Set<LocalDateTime>> userLuckyDates = new HashMap<>();
                    while (rs.next()) {
                        Long id = rs.getLong("id");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String email = rs.getString("email");
                        LocalDate birthday = rs.getTimestamp("birthday").toLocalDateTime().toLocalDate();
                        String password = rs.getString("password");
                        Set<Role> roles = Stream.of(rs.getString("roles").split(",")).map(Role::valueOf).collect(Collectors.toSet());

                        User tempUser = new User();
                        tempUser.setId(id);
                        tempUser.setFirstName(firstName);
                        tempUser.setLastName(lastName);
                        tempUser.setEmail(email);
                        tempUser.setBirthday(birthday);
                        tempUser.setPassword(password);
                        tempUser.setRoles(roles);

                        if (!userLuckyDates.containsKey(tempUser)) {
                            userLuckyDates.put(tempUser, new HashSet<>());
                        }
                        Timestamp luckyDate = rs.getTimestamp("lucky_datetime");
                        if (luckyDate != null) {
                            userLuckyDates.get(tempUser).add(luckyDate.toLocalDateTime());
                        }
                    }
                    userLuckyDates.forEach(User::setLuckyEvents);
                    return userLuckyDates.keySet();
                });
    }
}
