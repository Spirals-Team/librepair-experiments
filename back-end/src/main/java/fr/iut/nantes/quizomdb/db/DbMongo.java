package fr.iut.nantes.quizomdb.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import fr.iut.nantes.quizomdb.entite.Gamer;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static fr.iut.nantes.quizomdb.application.QuizOmdbApplication.config;
import static fr.iut.nantes.quizomdb.application.QuizOmdbApplication.log;
import static fr.iut.nantes.quizomdb.entite.Constants.*;

/**
 * @version 1.0
 * @see Idb
 * @since 1.0
 */
public class DbMongo implements Idb {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> gamerCollection;

    /**
     * init mongoDb
     */
    public DbMongo() throws ExceptionDB {
        try {
            log.debug("create mongodb ...");
            mongoClient = new MongoClient(
                    config.getMongo_url() + ":" + config.getMongo_port(),
                    MongoClientOptions.builder().retryWrites(false).build());
            log.debug("mongodbClient OK");
            database = mongoClient.getDatabase(config.getMongo_db_name());
            log.debug("database OK");
            gamerCollection = database.getCollection(config.getMongo_collection_gamer());
            log.debug("gamerCollection OK");
            log.debug("create mongodb OK");
        } catch (Exception e) {
            log.error("MongoDb error", e);
            throw new ExceptionDB("MongoDb error: " + e.getLocalizedMessage());
        }
    }

    /**
     * transform gamer in object for MongoDb
     *
     * @param gamer Gamer to transform
     * @param pwd   password of gamer
     * @return equivalent to gamer
     */
    private static Document toDocument(Gamer gamer, String pwd) {
        return new Document(DB_GAMER_LOGIN, gamer.getLogin())
                .append(DB_GAMER_PWD, pwd)
                .append(DB_GAMER_GOOD_ANSWERS, gamer.getGoodAnswers())
                .append(DB_GAMER_ANSWERS, gamer.getAnswers());
    }

    @Override
    public boolean setAnswers(String pseudo, int val) throws ExceptionDB {
        Document documents = gamerCollection.find(eq(DB_GAMER_LOGIN, pseudo)).first();
        if (documents == null) {
            throw new ExceptionDB("setAnswers: value not found for update");
        }

        gamerCollection.updateOne(
                eq(DB_GAMER_LOGIN, pseudo),
                new Document("$set", new Document(DB_GAMER_ANSWERS, val))
        );

        return true;
    }

    @Override
    public boolean setGoodAnswers(String pseudo, int val) throws ExceptionDB {
        Document documents = gamerCollection.find(eq(DB_GAMER_LOGIN, pseudo)).first();
        if (documents == null) {
            throw new ExceptionDB("setAnswers: value not found for update");
        }

        UpdateResult result = gamerCollection.updateOne(
                eq(DB_GAMER_LOGIN, pseudo),
                new Document("$set", new Document(DB_GAMER_GOOD_ANSWERS, val))
        );

        return result.wasAcknowledged();
    }

    @Override
    public boolean addGamer(Gamer gamer, String pwd) {
        try {
            gamerCollection.insertOne(toDocument(gamer, pwd));
        } catch (Exception e) {
            log.warn("addGamer error", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean supGamer(String pseudo) {
        try {
            DeleteResult deleteResult = gamerCollection.deleteOne(eq(DB_GAMER_LOGIN, pseudo));
            return deleteResult.getDeletedCount() == 1;
        } catch (Exception e) {
            log.warn("supGamer error", e);
            return false;
        }
    }


    @Override
    public int getAnswers(String pseudo) throws ExceptionDB {
        Document document = gamerCollection.find(eq(DB_GAMER_LOGIN, pseudo)).first();
        if (document == null) {
            throw new ExceptionDB("getAnswers: value not found for get");
        }
        return (int) document.get(DB_GAMER_ANSWERS);
    }

    @Override
    public int getGoodAnswers(String pseudo) throws ExceptionDB {
        Document document = gamerCollection.find(eq(DB_GAMER_LOGIN, pseudo)).first();
        if (document == null) {
            throw new ExceptionDB("getGoodAnswers: value not found for get");
        }
        return (int) document.get(DB_GAMER_GOOD_ANSWERS);
    }

    @Override
    public boolean connect(String login, String pwd) {
        String pwdDb = (String) gamerCollection.find(eq(DB_GAMER_LOGIN, login)).first().get(DB_GAMER_PWD);
        return pwdDb.equals(pwd);
    }

}
