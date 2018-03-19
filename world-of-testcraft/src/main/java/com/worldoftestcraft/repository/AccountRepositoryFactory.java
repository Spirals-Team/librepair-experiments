package com.worldoftestcraft.repository;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountRepositoryFactory {
    public static AccountRepository getInstance(){
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new AccountRepositoryImpl(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            return null;
        }
    }
}
