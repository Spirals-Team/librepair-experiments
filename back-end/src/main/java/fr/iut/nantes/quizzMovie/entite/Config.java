package fr.iut.nantes.quizzMovie.entite;

import fr.iut.nantes.quizzMovie.application.QuizzMovieApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @version 1.0
 * @since 1.0
 */
public class Config {

    private String DB = "";
    private String mongoUrl = "";
    private String mongoPort = "";
    private String mongoDbName = "";
    private String mongoCollectionGamer = "";

    /**
     * load config from file.
     *
     * @param url of config file
     * @since 1.0
     */
    public Config(String url) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(url));
            DB = properties.getProperty("db");
            mongoUrl = properties.getProperty("mongo_url");
            mongoPort = properties.getProperty("mongo_port");
            mongoDbName = properties.getProperty("mongo_db_name");
            mongoCollectionGamer = properties.getProperty("mongo_collection_gamer");
        } catch (IOException e) {
            QuizzMovieApplication.log.error("configFile", e);
        }
    }

    public String getDB() {
        return DB;
    }

    public String getMongoUrl() {
        return mongoUrl;
    }

    public String getMongoPort() {
        return mongoPort;
    }

    public String getMongoDbName() {
        return mongoDbName;
    }

    public String getMongoCollectionGamer() {
        return mongoCollectionGamer;
    }
}
