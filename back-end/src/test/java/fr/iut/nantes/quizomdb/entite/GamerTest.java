package fr.iut.nantes.quizomdb.entite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @version 1.0
 * @see Gamer
 * @since 1.0
 */
public class GamerTest {

    Gamer gamer;

    /**
     * @see Gamer#Gamer(String, int, int)
     * @since 1.0
     */
    @Before
    public void initialize() {
        gamer = new Gamer("login", 0, 0);
    }

    /**
     * @throws Exception
     * @see Gamer#getLogin()
     * @since 1.0
     */
    @Test
    public void getLogin() throws Exception {
        assertEquals("login", gamer.getLogin());
    }

    /**
     * @throws Exception
     * @see Gamer#getGoodAnswers()
     * @since 1.0
     */
    @Test
    public void getGoodAnswers() throws Exception {
        Gamer gamer2 = new Gamer("login", 0, 1);
        assertEquals(0, gamer2.getGoodAnswers());
    }

    /**
     * @throws Exception
     * @see Gamer#incrementGoodAnswers()
     * @since 1.0
     */
    @Test
    public void incrementGoodAnswers() throws Exception {
        int nb = gamer.getGoodAnswers();
        nb++;
        gamer.incrementGoodAnswers();
        assertEquals(nb, gamer.getGoodAnswers());
    }

    /**
     * @throws Exception
     * @see Gamer#getAnswers()
     * @since 1.0
     */
    @Test
    public void getAnswers() throws Exception {
        Gamer gamer2 = new Gamer("login", 0, 1);
        assertEquals(1, gamer2.getAnswers());
    }

    /**
     * @throws Exception
     * @see Gamer#incrementAnswers()
     * @since 1.0
     */
    @Test
    public void incrementAnswers() throws Exception {
        int nb = gamer.getAnswers();
        nb++;
        gamer.incrementAnswers();
        assertEquals(nb, gamer.getAnswers());
    }

    /**
     * @throws Exception
     * @see Gamer#equals(Object)
     * @since 1.0
     */
    @Test
    public void equals() throws Exception {
        Gamer gamer2 = new Gamer("login", 0, 1);
        Gamer gamer3 = new Gamer("login", 1, 0);
        assertTrue(gamer2.equals(gamer3));
    }

    /**
     * @throws Exception
     * @see Gamer#equals(Object)
     * @since 1.0
     */
    @Test
    public void notEquals() throws Exception {
        Gamer gamer2 = new Gamer("login", 0, 1);
        Gamer gamer3 = new Gamer("log", 1, 0);
        assertTrue(!gamer2.equals(gamer3));
    }

}