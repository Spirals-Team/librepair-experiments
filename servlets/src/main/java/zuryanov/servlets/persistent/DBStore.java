package zuryanov.servlets.persistent;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class DBStore implements Store {

    private static BasicDataSource dataSource;
    private static Properties prop;
    private static final DBStore DB_STORE = new DBStore();

    private static BasicDataSource getDataSource() {
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/servlets");
            ds.setUsername("root");
            ds.setPassword("root");
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            dataSource = ds;
            try {
                Class.forName("com.mysql.jdbc.Driver");
//                BasicDataSource dataSource = DBStore.getDataSource();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            DB_STORE.init();
        }
        return dataSource;
    }


    private DBStore() {
    }

    public static DBStore getInstance() {
        return DB_STORE;
    }

    public void init() {
        prop = new Properties();
        String propFileName = "mysql.properties";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(prop.getProperty("droptable"));
            statement.execute(prop.getProperty("createtable"));
            statement.execute(prop.getProperty("createroot"));
            statement.execute(prop.getProperty("droprole"));
            statement.execute(prop.getProperty("createrole"));
            statement.execute(prop.getProperty("insertroleone"));
            statement.execute(prop.getProperty("insertroletwo"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public  void add(String name) {
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("add"));) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String update(int id, String name) {
        String result = "";
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("update"));) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String delete(int id) {
        String result = "";
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("delete"));) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> findAll() {
        ArrayList<String> result = new ArrayList<>();
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("findall"));
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String findById(int id) {
        String result = "";
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("findbyid"));) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int sizeStore() {
        int result = 0;
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery(prop.getProperty("sizestore"));
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int findByName(String name) {
        int result = 0;
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("findbyname"));) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isCredentional(String login, String password) {
        boolean exist = false;
        ArrayList<String> result = new ArrayList<>();
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("iscredentional"));) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            rs.next();
            if (rs.getString(1).equals(password)) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public List<String> findRole() {
        ArrayList<String> result = new ArrayList<>();
        BasicDataSource dataSource = DBStore.getDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(prop.getProperty("findallrole"));
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}