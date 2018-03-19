package invisibleUniveristy.crud;

import java.sql.*;

public class CreatorRepositoryFactory{
    public static ICreatorRepository getInstance(){
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new CreatorRepositoryImpl(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            System.out.println("-----------CreatorRepositoryFactory--------------");
            e.printStackTrace();
            System.out.println("-----------CreatorRepositoryFactory--------------");
            return null;
        }
    }
}
