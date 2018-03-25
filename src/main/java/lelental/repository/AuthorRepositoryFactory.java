package lelental.repository;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Paweł Lelental
 **/
public class AuthorRepositoryFactory {


    public static AuthorRepository getInstance() throws SQLException {
        String dbConnectionURL = "jdbc:hsqldb:hsql://localhost/workdb";
        return new AuthorRepositoryImpl(DriverManager.getConnection(dbConnectionURL, "sa", ""));
    }
}
