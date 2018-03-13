package fr.iut.nantes.quizzMovie.db;

import fr.iut.nantes.quizzMovie.Utils;
import fr.iut.nantes.quizzMovie.entite.Gamer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.iut.nantes.quizzMovie.application.QuizzMovieApplication.log;
import static org.junit.Assert.*;

/**
 * @version 1.0
 * @see DbMongo
 * @since 1.0
 */
public class DbMongoTest {

    private Idb db;

    /**
     * @throws Exception
     * @since 1.0
     */
    @Before
    public void setUp() throws Exception {
        log.info("start test mongoDb");
        Utils.setupConfig();

        db = new DbMongo();

        db.addGamer(new Gamer("pseudo", 0, 0), "pwd");
    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @After
    public void tearDown() throws Exception {
        log.info("end test mongoDb");
        db.supGamer("pseudo");
    }

    //<editor-fold desc="setAnswers">

    /**
     * @throws Exception
     * @see DbMongo#setAnswers(String, int)
     * @since 1.0
     */
    @Test(expected = ExceptionDB.class)
    public void setAnswersForUnknownPseudo() throws Exception {
        db.setAnswers("UnknownPseudo", 10);
    }

    /**
     * @throws Exception
     * @see DbMongo#setAnswers(String, int)
     * @since 1.0
     */
    @Test
    public void setAnswersForNoUnknownPseudo() throws Exception {
        String pseudo = "pseudo";
        db.setAnswers(pseudo, 10);
        int answers = db.getAnswers(pseudo);
        assertEquals("setAnswers 10", 10, answers);
    }
    //</editor-fold>

    //<editor-fold desc="setGoodAnswers">

    /**
     * @throws Exception
     * @see DbMongo#setGoodAnswers(String, int)
     * @since 1.0
     */
    @Test(expected = ExceptionDB.class)
    public void setGoodAnswersForUnknownPseudo() throws Exception {
        db.setGoodAnswers("UnknownPseudo", 10);
    }

    /**
     * @throws Exception
     * @see DbMongo#setGoodAnswers(String, int)
     * @since 1.0
     */
    @Test
    public void setGoodAnswersForNoUnknownPseudo() throws Exception {
        String pseudo = "pseudo";
        db.setGoodAnswers(pseudo, 10);
        int goodAnswers = db.getGoodAnswers(pseudo);
        assertEquals("setAnswers 10", 10, goodAnswers);
    }
    //</editor-fold>

    //<editor-fold desc="add">

    /**
     * @throws Exception
     * @see DbMongo#addGamer(Gamer, String)
     * @since 1.0
     */
    @Test
    public void addGamerAlreadyExist() throws Exception {
        assertFalse(db.addGamer(new Gamer("pseudo", 0, 0), "pwd"));
    }

    //</editor-fold>

    //<editor-fold desc="addAndSup">

    /**
     * @throws Exception
     * @see DbMongo#addGamer(Gamer, String)
     * @see DbMongo#supGamer(String)
     * @since 1.0
     */
    @Test
    public void addAndSupGamerNotAlreadyExist() throws Exception {
        assertTrue(db.addGamer(new Gamer("pseudo2", 0, 0), "pwd"));
        db.supGamer("pseudo2");
    }
    //</editor-fold>


    //<editor-fold desc="sup">

    /**
     * @throws Exception
     * @see DbMongo#supGamer(String)
     * @since 1.0
     */
    @Test
    public void supGamerNotExist() throws Exception {
        assertFalse(db.supGamer("UnknownPseudo"));
    }
    //</editor-fold>


    //<editor-fold desc="getAnswers">

    /**
     * @throws Exception
     * @see DbMongo#getAnswers(String)
     * @since 1.0
     */
    @Test(expected = ExceptionDB.class)
    public void getAnswersGamerNotExist() throws Exception {
        db.getAnswers("UnknownPseudo");
    }

    /**
     * @throws Exception
     * @see DbMongo#getAnswers(String)
     * @since 1.0
     */
    @Test
    public void getAnswersGamerExist() throws Exception {
        assertEquals("Answers is no 0", 0, db.getAnswers("pseudo"));
    }
    //</editor-fold>


    //<editor-fold desc="getAnswers">

    /**
     * @throws Exception
     * @see DbMongo#getGoodAnswers(String)
     * @since 1.0
     */
    @Test(expected = ExceptionDB.class)
    public void getGoodAnswersGamerNotExist() throws Exception {
        db.getGoodAnswers("UnknownPseudo");
    }

    /**
     * @throws Exception
     * @see DbMongo#getGoodAnswers(String)
     * @since 1.0
     */
    @Test
    public void getGoodAnswersGamerExist() throws Exception {
        assertEquals("Answers is no 0", 0, db.getGoodAnswers("pseudo"));
    }
    //</editor-fold>


    //<editor-fold desc="connect">

    /**
     * @throws Exception
     * @see DbMongo#connect(String, String)
     * @since 1.0
     */
    @Test
    public void connectOk() throws Exception {
        assertTrue(db.connect("pseudo", "pwd"));
    }

    /**
     * @throws Exception
     * @see DbMongo#connect(String, String)
     * @since 1.0
     */
    @Test
    public void connectKo() throws Exception {
        assertFalse(db.connect("pseudo", "pwdKo"));
    }
    //</editor-fold>
}