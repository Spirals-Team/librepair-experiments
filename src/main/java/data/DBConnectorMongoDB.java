package data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Cherry Rose Semeña
 */
public class DBConnectorMongoDB {

    private MongoClient mongoClient = null;

//    private String URI = "mongodb://localhost/dbtest";
//      private String JAVA_ENV = System.getenv("JAVA_HOME");
//      private String PROD_MONGOURI = System.getenv("MONGO_URI");
    private String URI = "mongodb://" + USERNAME + ":" + PASSWORD + "@206.189.21.241:" + PORT;
    private static final String PORT = "27017"; // best left empty for default port
    private static final String USERNAME = "myUserAdmin";
    private static final String PASSWORD = "abc123";

    public MongoClient getConnection() {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.
        try {
//            if(PROD_MONGOURI != null){
//                URI = PROD_MONGOURI;
//            }else{
////                 String curdir = System.getProperty("user.dir");
//                Ini ini = new Ini(new File("/home/cjs/Desktop/DBTest Final Project/ProjectGutenberg_G9/src/main/java/data/secret.ini"));
//                URI = ini.get("header", "MONGO_URI");
//                System.out.println(URI);
//            }
            this.mongoClient = new MongoClient(new MongoClientURI(URI));

        } catch (Exception e) {
            System.out.println("ERROR IN MONGODB CONNECTION" + e.toString());
        }

        return this.mongoClient;
    }

    public void close() {
        this.mongoClient.close();
    }

}
