package DB.QueryCreators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoggerQueryCreator  {

    private static LoggerQueryCreator ourInstance = new LoggerQueryCreator();
    private Connection conn;

    public static LoggerQueryCreator getInstance() {
        return ourInstance;
    }

    private final static String SELECT_MILLIS_WHERE_MESSAGE = "select millis, logger_name, message from logger " +
            "where message like (?) and logger_name like (?)";

    private LoggerQueryCreator() {

    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }


    public PreparedStatement selectMillerMessage(String message, String logger_name) {
        PreparedStatement p = null;
        try {
            p = conn.prepareStatement(SELECT_MILLIS_WHERE_MESSAGE);
            p.setString(1, message);
            p.setString(2, logger_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


}
