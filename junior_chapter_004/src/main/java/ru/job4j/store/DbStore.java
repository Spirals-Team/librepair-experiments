package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.User;
import ru.job4j.model.UserBuilder;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 12.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class DbStore implements UserStore, AddressStore {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();

    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class);

    public static DbStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void closePoolConnections() {
        try {
            SOURCE.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DbStore() {
        SOURCE.setUrl("jdbc:postgresql://localhost:5432/dbstore");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("postgres");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = SOURCE.getConnection();
             Statement statement = connection.createStatement()) {
            StringBuilder sb = new StringBuilder();
            //Создаем таблицу для ролей пользователей.
            sb.append("CREATE TABLE IF NOT EXISTS users_role");
            sb.append("(id   SERIAL PRIMARY KEY,");
            sb.append("role VARCHAR(20));");

            //Добавляем 2 роли пользователей.
//            sb.append("INSERT INTO users_role (role) VALUES ('Administrator');");
//            sb.append("INSERT INTO users_role (role) VALUES ('User');");

            //Создаем таблицу для стран пользователей.
            sb.append("CREATE TABLE IF NOT EXISTS countries");
            sb.append("(id   SERIAL PRIMARY KEY,");
            sb.append("name VARCHAR(20));");

            //Добавляем 2 страны пользователей.
//            sb.append("INSERT INTO countries (name) VALUES ('USA');");
//            sb.append("INSERT INTO countries (name) VALUES ('Russia');");

            //Создаем таблицу для городов пользователей.
            sb.append("CREATE TABLE IF NOT EXISTS cities");
            sb.append("(id   SERIAL PRIMARY KEY,");
            sb.append("name VARCHAR(20), ");
            sb.append("country_id INTEGER REFERENCES countries (id));");

            //Добавляем по 2 города каждой стране.
//            sb.append("INSERT INTO cities (name, country_id) VALUES ('Moscow', 2);");
//            sb.append("INSERT INTO cities (name, country_id) VALUES ('Ekaterinburg', 2);");
//            sb.append("INSERT INTO cities (name, country_id) VALUES ('New York', 1);");
//            sb.append("INSERT INTO cities (name, country_id) VALUES ('San Francisco', 1);");

            //Создаем таблицу пользователей.
            sb.append("CREATE TABLE IF NOT EXISTS users");
            sb.append("(id     SERIAL PRIMARY KEY, ");
            sb.append("name   VARCHAR(100), ");
            sb.append("login  VARCHAR(100), ");
            sb.append("email  VARCHAR(100), ");
            sb.append("created TIMESTAMP, ");
            sb.append("password VARCHAR(100), ");
            sb.append("type_role INTEGER REFERENCES users_role (id), ");
            sb.append("citi_id INTEGER REFERENCES cities (id));");
//            sb.append("INSERT INTO users (name, login, email, created, password, type_role, citi_id) VALUES ('root', 'root', 'root@root.ru', '2018-02-05 12:00:00', 'root', 1, 2);");
            statement.execute(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStore(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO users(name, login, email, created, password, type_role, citi_id) VALUES (?,?,?,?,?,?,(SELECT id FROM cities WHERE cities.name = ?));")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, new Timestamp(user.getCreateDate().getTimeInMillis()));
            ps.setString(5, user.getPassword());
            ps.setInt(6, 2);
            ps.setString(7, user.getCiti());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStore(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE Users SET name=?, login=?, email=?, created=?, password=?, citi_id=(SELECT c.id FROM cities AS c WHERE c.name = ?)\n"
                     + "WHERE (id = ?);")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, new Timestamp(user.getCreateDate().getTimeInMillis()));
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getCiti());
            ps.setInt(7, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteStore(int id) {
        boolean resault = false;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE (id = ?);")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            resault = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resault;
    }

    @Override
    public List<User> findAllStore() {
        List<User> userList = new CopyOnWriteArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT u.id, u.name, u.login, u.email, u.created, u.password, c.name AS citi, con.name AS countries\n"
                    + "FROM users AS u\n"
                    + "INNER JOIN cities AS c ON u.citi_id = c.id\n"
                    + "INNER JOIN countries AS con ON con.id = c.country_id;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                Calendar createDate = Calendar.getInstance();
                Timestamp timestamp = new Timestamp(resultSet.getTimestamp("created").getTime());
                createDate.setTime(new Date(timestamp.getTime()));
                String password = resultSet.getString("password");
                String citi = resultSet.getString("citi");
                String countries = resultSet.getString("countries");
                User user = new UserBuilder()
                        .setId(id)
                        .setName(name)
                        .setLogin(login)
                        .setEmail(email)
                        .setCreateDate(createDate)
                        .setPassword(password)
                        .setCountries(countries)
                        .setCiti(citi)
                        .build();
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean findByLoginStore(String login) {
        boolean resault = false;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id, login FROM users where login=?;")) {
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            resault = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resault;
    }

    @Override
    public User findByIdStore(int id) {
        User user = null;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT u.id, u.name, u.login, u.email, u.created, u.password, c.name   AS citi, con.name AS countries\n"
                     + "FROM users AS u\n"
                     + "       INNER JOIN cities AS c ON u.citi_id = c.id\n"
                     + "       INNER JOIN countries AS con ON con.id = c.country_id\n"
                     + "WHERE (u.id = ?);")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                Calendar createDate = Calendar.getInstance();
                Timestamp timestamp = new Timestamp(resultSet.getTimestamp("created").getTime());
                createDate.setTime(new Date(timestamp.getTime()));
                String password = resultSet.getString("password");
                String citi = resultSet.getString("citi");
                String countries = resultSet.getString("countries");
                user = new UserBuilder()
                        .setId(id)
                        .setName(name)
                        .setLogin(login)
                        .setEmail(email)
                        .setCreateDate(createDate)
                        .setPassword(password)
                        .setCountries(countries)
                        .setCiti(citi)
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean isCredentional(String login, String password) {
        boolean resulte = false;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT login, password FROM Users WHERE (login = ?);")) {
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String loginDb = resultSet.getString("login");
                String passwordDb = resultSet.getString("password");
                if (login.equals(loginDb) && password.equals(passwordDb)) {
                    resulte = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resulte;
    }

    public int getUserRole(int id) {
        int typeRole = 0;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT type_role FROM Users WHERE (id = ?);")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                typeRole = Integer.parseInt(resultSet.getString("type_role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeRole;
    }

    public int getUserRoleByLogin(String login) {
        int typeRole = 0;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT type_role FROM Users WHERE (login = ?);")) {
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                typeRole = Integer.parseInt(resultSet.getString("type_role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeRole;
    }

    public Map<Integer, String> getAllRole() {
        Map<Integer, String> allRole = new HashMap<>();
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT id, role FROM users_role;");
            while (resultSet.next()) {
                allRole.put(resultSet.getInt("id"), resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRole;
    }

    public void updateUserRole(String id, String role) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE Users SET type_role=? WHERE (id = ?);")) {
            ps.setInt(1, Integer.parseInt(role));
            ps.setInt(2, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> findAllCountries() {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT name FROM countries;")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<String> findCitiesByCountry(String name) {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT name FROM cities WHERE country_id = (SELECT id FROM countries WHERE name = ?);")) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
