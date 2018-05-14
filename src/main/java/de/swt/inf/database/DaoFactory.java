package de.swt.inf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {

    /*  private static String dbDriver = "org.mariadb.jdbc.Driver";
        private static String dbUrl = "jdbc:mysql://localhost:3306/infswt";
        private static String username = "root";
        private static String password = "";
    */

    private static String dbDriver = "org.h2.Driver";
    private static String dbUrl = "jdbc:h2:mem:testdb";
    private static String username = "sa";
    private static String password = "";


    public static Connection getConnection(){
        try{
            Class.forName(dbDriver);
            Connection dbConnection = DriverManager.getConnection(dbUrl,username,password);
            return dbConnection;
        }
        catch(SQLException ex){
            System.err.println(ex);
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex);
        }
        return null;
    }

    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static TerminDao getTerminDao() {
        return new TerminDaoImpl(getConnection());
    }

    public static VCardDao getVCardDao() {
        return new VCardDaoImpl(getConnection());
    }

    public static CategoryDao getCategoryDao() {return new CategoryDaoImpl(getConnection());
    }

}
