package ru.job4j.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * UserStore class to interact with database.
 */
public class UserStore {

    private static final Logger LOG = LoggerFactory.getLogger(UserStore.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle("crud");
    private static final UserStore INSTANCE = new UserStore();

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (\n"
        + "  name        CHARACTER VARYING(100),\n"
        + "  login       CHARACTER VARYING(100),\n"
        + "  email       CHARACTER VARYING(100) PRIMARY KEY NOT NULL,\n"
        + "  create_date TIMESTAMP                          NOT NULL DEFAULT now()\n"
        + ")";

    private static final String ADD_USER =
        "INSERT INTO users (name, login, email)\n"
            + "VALUES (?, ?, ?)";

    private static final String FIND_ALL =
        "SELECT *\n"
            + "FROM users\n"
            + "ORDER BY email";

    private static final String DELETE_BY_EMAIL = "DELETE FROM users WHERE email = ?";

    private static final String REPLACE_BY_EMAIL =
        "UPDATE users\n"
            + "SET name = ?, login = ?, email = ?\n"
            + "WHERE email = ?";

    private Connection conn;

    private UserStore() {
        this.conn = connect();
        initDB();
    }

    /**
     * Singleton
     *
     * @return userstore
     */
    public static UserStore getInstance() {
        return INSTANCE;
    }

    /**
     * Add new user with unique email
     *
     * @param user user
     */
    public void addUser(final User user) {
        try (PreparedStatement pstmt =
                 conn.prepareStatement(ADD_USER)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLogin());
            pstmt.setString(3, user.getEmail());
            pstmt.executeUpdate();
            conn.commit();
            LOG.info("Add vacancy {}", user);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    /**
     * Update user
     *
     * @param email email id
     * @param user  new user
     */
    public void replace(final String email, final User user) {
        try (PreparedStatement prstmt =
                 conn.prepareStatement(REPLACE_BY_EMAIL)) {
            prstmt.setString(1, user.getName());
            prstmt.setString(2, user.getLogin());
            prstmt.setString(3, user.getEmail());
            prstmt.setString(4, email);
            prstmt.executeUpdate();
            conn.commit();
            LOG.info("Update user with email { {} }", email);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    /**
     * Remove user by email
     *
     * @param email email
     */
    public void delete(final String email) {
        try (PreparedStatement pstmt =
                 conn.prepareStatement(DELETE_BY_EMAIL)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            conn.commit();
            LOG.info("Delete item with email {}", email);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    /**
     * Find all users
     *
     * @return list of users
     */
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(FIND_ALL)) {
            users = listItems(rs);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    private List<User> listItems(final ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setLogin(rs.getString("login"));
            user.setEmail(rs.getString("email"));
            user.setCreateDate(rs.getDate("create_date"));
            users.add(user);
            LOG.info("Select user {}.", user);
        }
        return users;
    }

    private void initDB() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE);
            conn.commit();
            LOG.info("Create table if required.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    private Connection connect() {
        if (conn == null) {
            try {
                invokeDBDriver();
                conn = DriverManager.getConnection(RB.getString("db.url"));
                conn.setAutoCommit(false);
                LOG.info("Create connection to {}.", conn.getMetaData().getURL());
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return conn;
    }

    private void invokeDBDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
