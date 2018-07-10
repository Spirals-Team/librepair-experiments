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
    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("queries");
    private static final DbStore INSTANCE = new DbStore();

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
        try (PreparedStatement pstmt = conn.prepareStatement(
            RB_SQL.getString("select.user.by.id"))) {
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
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.users"))) {
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
                 conn.prepareStatement(RB_SQL.getString("insert.user"))) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLogin());
            pstmt.setString(3, user.getPassw());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getRegion());
            pstmt.setString(6, user.getCountry());
            pstmt.setString(7, user.getRole().toString());
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
                 conn.prepareStatement(RB_SQL.getString("update.user.by.id"))) {
            prstmt.setString(1, user.getName());
            prstmt.setString(2, user.getLogin());
            prstmt.setString(3, user.getPassw());
            prstmt.setString(4, user.getEmail());
            prstmt.setString(5, user.getRegion());
            prstmt.setString(6, user.getCountry());
            prstmt.setInt(7, id);
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
                 conn.prepareStatement(RB_SQL.getString("delete.user.by.id"))) {
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
        try (PreparedStatement pstmt = conn.prepareStatement(
            RB_SQL.getString("select.user.by.login"))) {
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
        try (PreparedStatement pstmt = conn.prepareStatement(
            RB_SQL.getString("select.user.by.login.passw"))) {
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
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.roles"))) {
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
        user.setRegion(rs.getString("region"));
        user.setCountry(rs.getString("country"));
        user.setRole(Role.roleFromName(rs.getString("user_role")));
        user.setCreateDate(rs.getDate("create_date"));
        return user;
    }

    public void initDB() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(RB_SQL.getString("create.table.roles"));
            stmt.execute(RB_SQL.getString("populate.roles"));
            stmt.execute(RB_SQL.getString("create.table.users"));
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
            stmt.execute(RB_SQL.getString("drop.table.users"));
            stmt.execute(RB_SQL.getString("drop.table.roles"));
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
                "root@email.com", "region", "country", Role.ADMIN));
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
