package fr.iut.nantes.quizomdb.db;

import com.mongodb.MongoWriteException;
import fr.iut.nantes.quizomdb.Utils;
import fr.iut.nantes.quizomdb.application.QuizOmdbApplication;
import fr.iut.nantes.quizomdb.entite.Config;
import fr.iut.nantes.quizomdb.entite.Gamer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.iut.nantes.quizomdb.application.QuizOmdbApplication.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @version 1.0
 * @see DbMongo
 * @since 1.0
 */
public class DbMongoTest {

    private Idb db;

    @Before
    public void setUp() throws Exception {
        log.info("start test mongoDb");
        Utils.setupConfig();

        db = new DbMongo();

        db.addGamer(new Gamer("pseudo", 0, 0), "pwd");
    }

    @After
    public void tearDown() throws Exception {
        log.info("end test mongoDb");
        db.supGamer("pseudo");
    }

    //<editor-fold desc="setAnswers">
    @Test(expected = ExceptionDB.class)
    public void setAnswersForUnknownPseudo() throws Exception {
        db.setAnswers("UnknownPseudo", 10);
    }

    @Test
    public void setAnswersForNoUnknownPseudo() throws Exception {
        String pseudo = "pseudo";
        db.setAnswers(pseudo, 10);
        int answers = db.getAnswers(pseudo);
        assertEquals("setAnswers 10", 10, answers);
    }
    //</editor-fold>

    //<editor-fold desc="setGoodAnswers">
    @Test(expected = ExceptionDB.class)
    public void setGoodAnswersForUnknownPseudo() throws Exception {
        db.setGoodAnswers("UnknownPseudo", 10);
    }

    @Test
    public void setGoodAnswersForNoUnknownPseudo() throws Exception {
        String pseudo = "pseudo";
        db.setGoodAnswers(pseudo, 10);
        int goodAnswers = db.getGoodAnswers(pseudo);
        assertEquals("setAnswers 10", 10, goodAnswers);
    }
    //</editor-fold>

    //<editor-fold desc="add">
    @Test
    public void addGamerAlreadyExist() throws Exception {
        assertFalse(db.addGamer(new Gamer("pseudo", 0, 0), "pwd"));
    }
    //</editor-fold>

    //<editor-fold desc="addAndSup">
    @Test
    public void addAndSupGamerNotAlreadyExist() throws Exception {
        assertTrue(db.addGamer(new Gamer("pseudo2", 0, 0), "pwd"));
        db.supGamer("pseudo2");
    }
    //</editor-fold>


    //<editor-fold desc="sup">
    @Test
    public void supGamerNotExist() throws Exception {
        assertFalse(db.supGamer("UnknownPseudo"));
    }
    //</editor-fold>


    //<editor-fold desc="getAnswers">
    @Test(expected = ExceptionDB.class)
    public void getAnswersGamerNotExist() throws Exception {
        db.getAnswers("UnknownPseudo");
    }

    @Test
    public void getAnswersGamerExist() throws Exception {
        assertEquals("Answers is no 0", 0, db.getAnswers("pseudo"));
    }
    //</editor-fold>


    //<editor-fold desc="getAnswers">
    @Test(expected = ExceptionDB.class)
    public void getGoodAnswersGamerNotExist() throws Exception {
        db.getGoodAnswers("UnknownPseudo");
    }

    @Test
    public void getGoodAnswersGamerExist() throws Exception {
        assertEquals("Answers is no 0", 0, db.getGoodAnswers("pseudo"));
    }
    //</editor-fold>


    //<editor-fold desc="connect">
    @Test
    public void connectOk() throws Exception {
        assertTrue(db.connect("pseudo", "pwd"));
    }

    @Test
    public void connectKo() throws Exception {
        assertFalse(db.connect("pseudo", "pwdKo"));
    }
    //</editor-fold>
}