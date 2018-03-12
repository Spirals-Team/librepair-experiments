package fr.iut.nantes.quizomdb.controler;

import fr.iut.nantes.quizomdb.Utils;
import fr.iut.nantes.quizomdb.db.ExceptionDB;
import fr.iut.nantes.quizomdb.entite.Gamer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @version 1.0
 * @see ControlerGamer
 * @since 1.0
 */
public class TestControlerGamer {

    ControlerGamer control;

    /**
     * @since 1.0
     */
    @Before
    public void initialize() {
        Utils.setupConfig();
        control = new ControlerGamer();
        control.getDb().addGamer(new Gamer("test", 0, 0), "test");
        control.getDb().addGamer(new Gamer("login", 0, 0), "login");
    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @After
    public void tearDown() throws Exception {
        control.getDb().supGamer("test");
        control.getDb().supGamer("login");
    }

    /**
     * @see ControlerGamer#addGamer(String, int, int)
     * @see ControlerGamer#disconnect(String)
     * @since 1.0
     */
    @Test
    public void testAddGamerAndDisconnect() {
        assertNull(control.getGamer("login"));
        control.addGamer("login", 0, 0);
        assertNotNull(control.getGamer("login"));
        control.disconnect("login");
        assertNull(control.getGamer("login"));
    }

    /**
     * @see ControlerGamer#addGamer(String, int, int)
     * @since 1.0
     */
    @Test
    public void testGetLogins() {
        assertTrue(!control.getLogins().contains("login"));
        control.addGamer("login", 0, 0);
        assertTrue(control.getLogins().contains("login"));

    }

    /**
     * @throws Exception
     * @see ControlerGamer#login(String, String)
     * @since 1.0
     */
    @Test(expected = Exception.class)
    public void testLoginInvalid() throws Exception {
        control.login("falseLogin", "falsePassword");
    }

    /**
     * @throws Exception
     * @see ControlerGamer#addGamer(String, int, int)
     * @since 1.0
     */
    @Test
    public void testAddGamerReturn() throws Exception {
        String value1 = control.addGamer("test", 0, 0);
        String value2 = control.addGamer("test", 0, 0);
        assertTrue(value1.equals(value2));
    }

    /**
     * @see ControlerGamer#addGamer(String, int, int)
     * @see ControlerGamer#getGamer(String)
     * @see ControlerGamer#disconnect(String)
     * @since 1.0
     */
    @Test
    public void testDisconnectChangeValue() {
        String pseudo = "test";
        try {
            control.addGamer(pseudo, 0, 0);

            int valBd = control.getDb().getAnswers(pseudo);
            int valLocal = control.getGamer(pseudo).getAnswers();
            assertTrue(valBd == valLocal);

            control.getGamer(pseudo).incrementAnswers();
            valBd = control.getDb().getAnswers(pseudo);
            valLocal = control.getGamer(pseudo).getAnswers();
            assertTrue(valBd != valLocal);

            control.disconnect(pseudo);
            valBd = control.getDb().getAnswers(pseudo);
            assertTrue(valBd == valLocal);
        } catch (ExceptionDB exceptionDB) {
            exceptionDB.printStackTrace();
        }

    }

}
