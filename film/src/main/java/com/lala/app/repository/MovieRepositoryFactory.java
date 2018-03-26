package com.lala.app.repository;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MovieRepositoryFactory {
    public static MovieRepository getInstance() {

        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new MovieRepositoryImpl(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            return null;
        }
}
}