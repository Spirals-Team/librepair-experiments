package data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.ini4j.Ini;

/**
 *
 * @author Cherry Rose Semeña
 */

public class DBConnectorMongoDB { 
    
    private MongoClient mongoClient= null;
    
//    private String URI = "mongodb://localhost/dbtest";
      private String JAVA_ENV = System.getenv("JAVA_HOME");
      private String PROD_MONGOURI = System.getenv("MONGO_URI");
      private String URI ;
//              "mongodb://myUserAdmin:abc123@206.189.21.241:27017/gutenberg";
//    mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
//    mongodb://root:secret@db1.server.com:27027
      
    
    public MongoClient getConnection(){
        try{
            if(JAVA_ENV.equals("/usr/lib/jvm/java-8-oracle")){
//                String curdir = System.getProperty("user.dir");
                Ini ini = new Ini(new File("/home/cjs/Desktop/DBTest Final Project/ProjectGutenberg_G9/src/main/java/data/secret.ini"));
                URI = ini.get("header", "MONGO_URI");
                System.out.println(URI);
            }else{
                URI = PROD_MONGOURI;
            }
           this.mongoClient = new MongoClient(new MongoClientURI(URI));
        
        }catch(Exception e){
            System.out.println("ERROR IN MONGODB CONNECTION" + e.toString());
        }
         
        return this.mongoClient;
    }
    
    public void close(){
        this.mongoClient.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
//            Properties p = new Properties();
//                p.load(new FileInputStream("src/main/resources/secret.ini"));
//                String URI = p.getProperty("MONGO_URI");
//                System.out.println(URI);
//System.out.println("Working Directory = " +
//              System.getProperty("user.dir"));
//Ini ini = new Ini(new File("src/main/resources/secret.ini"));
//System.out.println(ini.get("header", "MONGO_URI"));
//        DBConnectorMongoDB db = new DBConnectorMongoDB();
//        System.out.println(db.getConnection());
    }
   
}
