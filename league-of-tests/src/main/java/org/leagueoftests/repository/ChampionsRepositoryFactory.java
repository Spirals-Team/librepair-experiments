package org.leagueoftests.repository;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ChampionsRepositoryFactory {
    public static ChampionsRepository getInstance() {
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new ChampionsRepositoryImplementation(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            return null;
        }
    }
}