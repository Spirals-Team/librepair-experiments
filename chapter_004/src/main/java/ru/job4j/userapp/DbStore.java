package ru.job4j.userapp;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DbStore class to interact with database.
 */
public class DbStore {

    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class);

    private static final ResourceBundle RB = ResourceBundle.getBundle("userapp");
    private static final DbStore INSTANCE = new DbStore();

    private static final String CREATE_USERS_TABLE =
        "CREATE TABLE IF NOT EXISTS users (\n"
            + "  id           SERIAL PRIMARY KEY,\n"
            + "  name         CHARACTER VARYING(100),\n"
            + "  login        CHARACTER VARYING(100) UNIQUE NOT NULL,\n"
            + "  passw        CHARACTER VARYING(100) NOT NULL ,\n"
            + "  email        CHARACTER VARYING(100) UNIQUE NOT NULL,\n"
            + "  user_role    CHARACTER VARYING(100) NOT NULL REFERENCES roles (role),\n"
            + "  create_date  TIMESTAMP              NOT NULL DEFAULT now()\n"
            + ")";

    private static final String CREATE_ROLES_TABLE =
        "CREATE TABLE IF NOT EXISTS roles (\n"
            + "  role CHARACTER VARYING(100) PRIMARY KEY\n"
            + ");";

    private static final String INIT_ROLES_TABLE =
        "INSERT INTO roles (role)\n"
            + "  SELECT 'ADMIN'\n"
            + "  WHERE NOT exists(SELECT 1\n"
            + "                   FROM roles\n"
            + "                   WHERE role = 'ADMIN');\n"
            + "INSERT INTO roles (role)\n"
            + "  SELECT 'USER'\n"
            + "  WHERE NOT exists(SELECT 1\n"
            + "                   FROM roles\n"
            + "                   WHERE role = 'USER');\n"
            + "INSERT INTO roles (role)\n"
            + "  SELECT 'GUEST'\n"
            + "  WHERE NOT exists(SELECT 1\n"
            + "                   FROM roles\n"
            + "                   WHERE role = 'GUEST');";

    private static final String ADD_USER =
        "INSERT INTO users (name, login, passw, email, user_role)\n"
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ALL_USERS =
        "SELECT *\n"
            + "FROM users\n"
            + "ORDER BY id";

    private static final String GET_ALL_ROLES = "SELECT *\nFROM roles";

    private static final String DELETE_BY_EMAIL = "DELETE FROM users WHERE id = ?";

    private static final String REPLACE_BY_EMAIL =
        "UPDATE users\n"
            + "SET name = ?, login = ?, passw = ?, email = ?\n"
            + "WHERE id = ?";

    private static final String FIND_BY_LOGIN =
        "SELECT *\n"
            + "FROM users\n"
            + "WHERE login = ?\n";

    private static final String FIND_BY_ID =
        "SELECT *\n"
            + "FROM users\n"
            + "WHERE id = ?\n";

    private static final String FIND_BY_LOGIN_AND_PASSW =
        "SELECT *\n"
            + "FROM users\n"
            + "WHERE login = ? AND passw = ?\n";

    private Connection conn;

    private DbStore() {
        this.conn = connect();
        initDB();
        addRootIfRequired();
    }

    /**
     * Singleton
     *
     * @return userstore
     */
    public static DbStore getInstance() {
        return INSTANCE;
    }

    /**
     * Get user by id
     *
     * @param id id
     * @return user
     */
    public User getUser(final Integer id) {
        User user = null;
        try (PreparedStatement pstmt = conn.prepareStatement(FIND_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    /**
     * Find all users
     *
     * @return list of users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_USERS)) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    /**
     * Add new user with unique email
     *
     * @param user user
     */
    public boolean addUser(final User user) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.prepareStatement(ADD_USER)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLogin());
            pstmt.setString(3, user.getPassw());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getRole().toString());
            int rowCount = pstmt.executeUpdate();
            conn.commit();
            if (rowCount == 1) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
        return result;
    }

    /**
     * Update user
     *
     * @param id   id
     * @param user new user
     */
    public boolean replace(final int id, final User user) {
        boolean result = false;
        try (PreparedStatement prstmt =
                 conn.prepareStatement(REPLACE_BY_EMAIL)) {
            prstmt.setString(1, user.getName());
            prstmt.setString(2, user.getLogin());
            prstmt.setString(3, user.getPassw());
            prstmt.setString(4, user.getEmail());
            prstmt.setInt(5, id);
            int rowCount = prstmt.executeUpdate();
            conn.commit();
            if (rowCount == 1) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
        return result;
    }

    /**
     * Remove user by id
     *
     * @param id id
     */
    public boolean delete(final int id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.prepareStatement(DELETE_BY_EMAIL)) {
            pstmt.setInt(1, id);
            int rowCounter = pstmt.executeUpdate();
            conn.commit();
            if (rowCounter == 1) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
        return result;
    }

    /**
     * Find first user by login
     *
     * @param login login
     * @return user
     */
    public User findFirstByLogin(final String login) {
        User user = null;
        try (PreparedStatement pstmt = conn.prepareStatement(FIND_BY_LOGIN)) {
            pstmt.setString(1, login);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    /**
     * Find user by login and password
     *
     * @param login login
     * @param passw password
     * @return user
     */
    public User findUserByLoginAndPassw(final String login, final String passw) {
        User user = null;
        try (PreparedStatement pstmt =
                 conn.prepareStatement(FIND_BY_LOGIN_AND_PASSW)) {
            pstmt.setString(1, login);
            pstmt.setString(2, passw);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    /**
     * Get roles
     *
     * @return roles
     */
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL_ROLES)) {
            while (rs.next()) {
                roles.add(Role.roleFromName(rs.getString("role")));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return roles;
    }

    private User extractUserFromResultSet(final ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setPassw(rs.getString("passw"));
        user.setEmail(rs.getString("email"));
        user.setRole(Role.roleFromName(rs.getString("user_role")));
        user.setCreateDate(rs.getDate("create_date"));
        return user;
    }

    public void initDB() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_ROLES_TABLE);
            stmt.execute(INIT_ROLES_TABLE);
            stmt.execute(CREATE_USERS_TABLE);
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

    public void cleanDB() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE users");
            stmt.execute("DROP TABLE roles");
            conn.commit();
            LOG.info("Clean database.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    public void addRootIfRequired() {
        if (findFirstByLogin("root") == null) {
            addUser(new User("root", "root", "root",
                "root@email.com", Role.ADMIN));
        }
    }

    public static DataSource setupDataSource(String connectURI) {
        ConnectionFactory connectionFactory =
            new DriverManagerConnectionFactory(connectURI, null);
        PoolableConnectionFactory poolableConnectionFactory =
            new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =
            new GenericObjectPool<>(poolableConnectionFactory);
        return new PoolingDataSource<>(connectionPool);
    }

    private Connection connect() {
        if (conn == null) {
            try {
                invokeDBDriver();
                conn = setupDataSource(RB.getString("db.url")).getConnection();
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
