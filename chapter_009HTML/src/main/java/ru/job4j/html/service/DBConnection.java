package ru.job4j.html.service;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.html.model.Address;
import ru.job4j.html.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class DBConnection {
    private static final Logger LOG = LogManager.getLogger(DBConnection.class);
    private static final String DB_SETTINGS_FILE = "settings.properties";
    private static final String SQL_FILE = "sql.properties";
    private Properties sql;
    private BasicDataSource dataSource;

    private DBConnection() {
        initParam();
        createTables();
    }

    public boolean checkLogin(String login) {
        boolean exists = false;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.getProperty("GET_USER_BY_LOGIN"))
        ) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return exists;
    }

    public boolean addNewUser(User user) {
        boolean add = false;
        try (final Connection connection = this.dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql.getProperty("ADD_NEW_USER"))
        ) { //login, name, email, password, countryId, townId, role, date
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5, Integer.parseInt(user.getCountryId()));
            ps.setInt(6, Integer.parseInt(user.getTownId()));
            ps.setString(7, user.getRole());
            ps.setTimestamp(8, user.getData());
           add = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return add;
    }


    private static class DBConnHolder {
        private static final DBConnection INSTANCE = new DBConnection();
    }
    public static DBConnection getInstance() {
        return DBConnHolder.INSTANCE;
    }
    private void initParam() {
        try (InputStream isDb = getClass().getClassLoader().getResourceAsStream(DB_SETTINGS_FILE);
             InputStream isSql = getClass().getClassLoader().getResourceAsStream(SQL_FILE)
        ) {
            this.sql = new Properties();
            this.sql.load(isSql);
            Properties pr = new Properties();
            pr.load(isDb);
            this.dataSource = new BasicDataSource();
            this.dataSource.setDriverClassName(pr.getProperty("db.class"));
            this.dataSource.setUrl(pr.getProperty("db.url"));
            this.dataSource.setUsername(pr.getProperty("db.user"));
            this.dataSource.setPassword(pr.getProperty("db.password"));
            this.dataSource.setMaxIdle(1000);
            this.dataSource.setMinIdle(100);

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    public void close() {
        try {
            this.dataSource.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    public List<Address> getAllCountries() {
        List<Address> list = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection();
             final Statement st = connection.createStatement();
             final ResultSet rs = st.executeQuery(sql.getProperty("GET_ALL_COUNTRIES"))
        ) {
            while (rs.next()) {
                list.add(new Address(rs.getString("id"), rs.getString("country")));
            }
        } catch (SQLException e) {
        LOG.error(e.getMessage(), e);
        }
        return list;
    }
    private void createTables() {
        try (final Connection connection = dataSource.getConnection();
             final Statement st = connection.createStatement()
        ) {
            st.executeUpdate(sql.getProperty("CREATE_TABLE_COUNTRIES"));
            st.executeUpdate(sql.getProperty("CREATE_TABLE_TOWNS"));

            st.executeUpdate(sql.getProperty("CREATE_TABLE_ROLE"));
            st.executeUpdate(sql.getProperty("CREATE_TABLE_USERS"));
            try (final ResultSet rs = st.executeQuery(sql.getProperty("SELECT_ALL_USERS"))) {
                if (!rs.next()) {
                    st.executeUpdate(sql.getProperty("CREATE_ROLES"));
                    st.executeUpdate(sql.getProperty("CREATE_ROOT_USER"));
                }
            }
            try (ResultSet rs = st.executeQuery(sql.getProperty("GET_ALL_COUNTRIES"))) {
                if (!rs.next()) {
                    st.executeUpdate(sql.getProperty("ADD_COUNTIES"));
                    st.executeUpdate(sql.getProperty("ADD_TOWNS"));
                }
            }
        } catch (SQLException e) {
           LOG.error(e.getMessage(), e);
        }
    }
    public List<Address> getAllTowns(String id) {
        List<Address> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql.getProperty("SELECT_TOWNS_BY_ID"))
        ) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Address(rs.getString("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
          LOG.error(e.getMessage(), e);
        }
        return list;
    }
}
