package fr.iut.nantes.quizomdb.entite;

import fr.iut.nantes.quizomdb.application.QuizOmdbApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @version 1.0
 * @since 1.0
 */
public class Config {

    private String DB = "";
    private String mongo_url = "";
    private String mongo_port = "";
    private String mongo_db_name = "";
    private String mongo_collection_gamer = "";

    public Config(String url) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(url));
            DB = properties.getProperty("db");
            mongo_url = properties.getProperty("mongo_url");
            mongo_port = properties.getProperty("mongo_port");
            mongo_db_name = properties.getProperty("mongo_db_name");
            mongo_collection_gamer = properties.getProperty("mongo_collection_gamer");
        } catch (IOException e) {
            QuizOmdbApplication.log.error("configFile", e);
        }
    }

    public String getDB() {
        return DB;
    }

    public String getMongo_url() {
        return mongo_url;
    }

    public String getMongo_port() {
        return mongo_port;
    }

    public String getMongo_db_name() {
        return mongo_db_name;
    }

    public String getMongo_collection_gamer() {
        return mongo_collection_gamer;
    }
}
