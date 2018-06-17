package de.swt.inf.database;

import de.swt.inf.model.Calendar;
import de.swt.inf.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean addUser(User user) {
        try {
            int confirmed = user.getConfirmedUser() ? 1 : 0;
            //int userPreferences = user.getUserPreferences().getId();
            String userPreferences = null;
            //SQL:
            //INSERT INTO `user` (`USER_ID`, `Username`, `Password`, `Email`, `Firstname`, `Lastname`, `ConfirmedUser`, `UserPreferences`, `WeatherReport`, `CALENDAR_ID`) VALUES ('10001', 'test', 'test', 'test@test.de', 'testname', 'nn', NULL, NULL, NULL, '1')
            //String query = "INSERT INTO use` (USER_ID, Username, Password, Email, Firstname, Lastname, ConfirmedUser, UserPreferences, WeatherReport, CALENDAR_ID) VALUES VALUES ('null', '" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getEmail() + "', '" + user.getFirstname() + "', '" + user.getLastname() + "', '" + confirmed + "', '" + userPreferences + "', NULL, '" + user.getCalendar().getCALENDAR_ID() + "'";
            String query = "INSERT INTO user VALUES ('null', '" + user.getUsername() + "', '" + user.getPassword()
                    + "', '" + user.getEmail() + "', '" + user.getFirstname() + "', '" + user.getLastname() +
                    "', NULL, NULL, NULL, '" + user.getCalendar().getCALENDAR_ID() + "')";
            query = query.replaceAll("'null'", "NULL");
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * de.swt..inf.database.DaoUser#getUser(int)
     */
    public User getUser(int USER_ID) {

        try {
            String query = "SELECT * FROM user WHERE USER_ID = " + USER_ID;
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            User tmpUser = new User();
            //rs.next();
            tmpUser.setUSER_ID(rs.getInt("USER_ID"));
            tmpUser.setUsername(rs.getString("USERNAME"));
            tmpUser.setPassword(rs.getString("PASSWORD"));
            tmpUser.setEmail(rs.getString("EMAIL"));
            tmpUser.setFirstname(rs.getString("FIRSTNAME"));
            tmpUser.setLastname(rs.getString("LASTNAME"));
            tmpUser.setConfirmedUser(rs.getBoolean("CONFIRMEDUSER"));
            //tmpUreturn null;ser.setUserPreferences(rs.getInt("USERPREFERENCES"));
            //tmpUser.setWeatherReport(rs.getInt("WEATHERREPORT"));
            int calendarId = rs.getInt("CALENDAR_ID");
            Calendar userCalendar = DaoFactory.getCalendarDao().getCalendarByID(calendarId);
            tmpUser.setCalendar(userCalendar);
            return tmpUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByName(String username) {
        try {
            //String query = "SELECT * FROM user WHERE USERNAME = " + username;
            String query = "SELECT * FROM user";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            User tmpUser = new User();
            while (rs.next()) {
                if (rs.getString(rs.findColumn("USERNAME")).equals(username)) {
                    tmpUser.setUSER_ID(rs.getInt("USER_ID"));
                    tmpUser.setUsername(rs.getString("USERNAME"));
                    tmpUser.setPassword(rs.getString("PASSWORD"));
                    tmpUser.setEmail(rs.getString("EMAIL"));
                    tmpUser.setFirstname(rs.getString("FIRSTNAME"));
                    tmpUser.setLastname(rs.getString("LASTNAME"));
                    tmpUser.setConfirmedUser(rs.getBoolean("CONFIRMEDUSER"));
                    //tmpUreturn null;ser.setUserPreferences(rs.getInt("USERPREFERENCES"));
                    //tmpUser.setWeatherReport(rs.getInt("WEATHERREPORT"));
                    int calendarId = rs.getInt("CALENDAR_ID");
                    Calendar userCalendar = DaoFactory.getCalendarDao().getCalendarByID(calendarId);
                    tmpUser.setCalendar(userCalendar);
                    return tmpUser;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isEmailInUse(String eMail) {
        try {
            String query = "SELECT * FROM user";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                if (rs.getString(rs.findColumn("EMAIL")).equals(eMail)) {
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(int USER_ID) {
        return false;
    }

    public boolean deleteUser(int USER_ID) {
        return false;
    }

}
